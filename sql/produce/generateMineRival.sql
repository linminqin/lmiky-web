DELIMITER $$

USE `BattleDotaLogicDB`$$

DROP PROCEDURE IF EXISTS `generateMineRival`$$

CREATE DEFINER=`root`@`%` PROCEDURE `generateMineRival`(IN pId BIGINT)
BEGIN	
	DECLARE playerGrade INT;	/*玩家等级*/
	DECLARE rivalPlayerId BIGINT;	/*对手玩家ID*/
	DECLARE loopIndex INT DEFAULT 1;	/*循环标示*/
	DECLARE stageCount INT DEFAULT 10;	/*关卡数*/
	/*对手玩家等级区间,每个关卡以“;”分隔，关卡内各个概率分段以“,”分隔*/
	DECLARE gradeRanges VARCHAR(256) DEFAULT '-8,-7,-6;-7,-6,-5;-5,-4,-3;-4,-3,-2;-3,-2,-1;-2,-1,-0;-1,0,1;-1,0,1;0,1,2;1,2,3;';
	/*对手玩家等级区间百分比概率,单位为%，每个关卡以“;”分隔，关卡内各个概率分段以“,”分隔*/
	DECLARE gradePercents VARCHAR(256) DEFAULT '80,10,10;80,10,10;80,10,10;80,10,10;80,10,10;80,10,10;80,10,10;80,10,10;80,10,10;80,10,10;';
	
	DECLARE gradeRange VARCHAR(16);		/*每个关卡区间段*/
	DECLARE gradePercent VARCHAR(16);
	DECLARE perGradeRange VARCHAR(8);	/*每个区间*/
	DECLARE perGradePercent VARCHAR(8);
	DECLARE rangeCount INT;			/*区间数*/
	DECLARE rangeLoopIndex INT DEFAULT 1;
	
	DECLARE randomValue INT DEFAULT 1;
	DECLARE gradeRangeValue INT DEFAULT 0;		
	DECLARE gradePercentValue INT DEFAULT 0;
	DECLARE lastGradeRangeValue INT DEFAULT 0;	/*上一个区间值*/
	
	
	
	/*定义错误*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION  ROLLBACK;
	
	START TRANSACTION; /*开始事务*/
	
	-- SET stageCount = CHAR_LENGTH(gradeRanges) - CHAR_LENGTH(REPLACE(gradeRanges, ';', '')) + 1;	/*计算关卡数*/
		
	SELECT grade INTO playerGrade FROM t_player_info i WHERE i.playerId = pId; /*获取玩家等级*/
	-- select playerGrade;
	DELETE FROM t_mine_battle_rival_hero WHERE playerId = pId; /*清空旧数据*/
	/*循环计算对手*/
	WHILE loopIndex <= stageCount		
	DO
		SET randomValue = FLOOR((RAND() * 100)) + 1;	/*随机数*/
		SET gradeRange = SUBSTRING_INDEX(SUBSTRING_INDEX(gradeRanges, ';', loopIndex), ';', -1); 	/*获取当前关卡的等级区间配置*/
		SET gradePercent = SUBSTRING_INDEX(SUBSTRING_INDEX(gradePercents, ';', loopIndex), ';', -1);	/*获取当前关卡的等级区间百分比配置*/
		SET rangeCount = CHAR_LENGTH(gradeRange) - CHAR_LENGTH(REPLACE(gradeRange, ',', '')) + 1;	/*计算区间数*/
		WHILE rangeLoopIndex <= rangeCount		
		DO
			SET perGradeRange = SUBSTRING_INDEX(SUBSTRING_INDEX(gradeRange, ',', rangeLoopIndex), ',', -1);     /*获取当前的等级区间值*/
			SET perGradePercent = SUBSTRING_INDEX(SUBSTRING_INDEX(gradePercent, ',', rangeLoopIndex), ',', -1); /*获取当前的等级区间百分比值*/
			SET gradePercentValue = gradePercentValue + perGradePercent;	/*总概率100*/
			IF(randomValue <= gradePercentValue) THEN	/*在概率内*/
				SET gradeRangeValue = perGradeRange;
				SET rangeLoopIndex = rangeCount + 1; /*结束循环*/
			ELSE 
				SET rangeLoopIndex = rangeLoopIndex + 1;
			END IF;			
		END WHILE;
					
		/*获取符合条件的玩家ID*/
		SET rivalPlayerId = NULL;
		SELECT c.playerId INTO rivalPlayerId FROM t_mine_defend_team_capacity c, t_player_info i
			WHERE c.playerId = i.playerId AND i.grade = playerGrade + gradeRangeValue 
			ORDER BY RAND() LIMIT 0, 1;
		IF(rivalPlayerId IS NULL) THEN /*未找到符合条件的玩家*/
			SET gradeRangeValue = lastGradeRangeValue;	/*用上一次的值来计算*/
			SELECT c.playerId INTO rivalPlayerId FROM t_mine_defend_team_capacity c, t_player_info i
				WHERE c.playerId = i.playerId AND i.grade = playerGrade + gradeRangeValue 
				ORDER BY RAND() LIMIT 0, 1;
			IF(rivalPlayerId IS NULL) THEN
				SET gradeRangeValue = 0;	/*匹配玩家的等级*/
				SELECT c.playerId INTO rivalPlayerId FROM t_mine_defend_team_capacity c, t_player_info i
					WHERE c.playerId = i.playerId AND i.grade = playerGrade + gradeRangeValue 
					ORDER BY RAND() LIMIT 0, 1;
			END IF;
		END IF;
		
		SET lastGradeRangeValue = gradeRangeValue;
		
		/*获取玩家守矿队伍*/
		INSERT INTO t_mine_battle_rival_hero(playerId, rivalPlayerId, stage, heroId, equip1, equip2, equip3, equip4, equip5, equip6, 
			equipGrade1, equipGrade2, equipGrade3, equipGrade4, equipGrade5, equipGrade6, skillGrade1, skillGrade2, skillGrade3, skillGrade4, growGrade, starGrade, grade)
		SELECT pId, rivalPlayerId, loopIndex, heroId, equip1, equip2, equip3, equip4, equip5, equip6, 
			equipGrade1, equipGrade2, equipGrade3, equipGrade4, equipGrade5, equipGrade6, skillGrade1, skillGrade2, skillGrade3, skillGrade4, growGrade, starGrade, grade
		FROM t_mine_defend_team t WHERE t.playerId = rivalPlayerId;
		
		SET rangeLoopIndex = 1;
		SET gradeRangeValue = 0;
		SET gradePercentValue = 0;
		SET loopIndex = loopIndex + 1;
		
	END WHILE;
	
	COMMIT; /*事务提交*/ 
	
    END$$

DELIMITER ;