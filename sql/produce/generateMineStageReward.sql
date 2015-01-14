DELIMITER $$

USE `BattleDotaLogicDB`$$

DROP PROCEDURE IF EXISTS `generateMineStageReward`$$

CREATE DEFINER=`root`@`%` PROCEDURE `generateMineStageReward`(IN pId BIGINT, IN stageIndex INT)
BEGIN
	DECLARE vipGrade INT;	/*玩家VIP等级*/
	DECLARE loopIndex INT DEFAULT 1;	/*循环标示*/
	DECLARE maxRewardItemCount INT DEFAULT 3;	/*最大奖励物品数*/
	DECLARE countFlag INT DEFAULT 0;	/*计数标示*/
	DECLARE randomValue INT DEFAULT 0;	/*随机值*/
	
	DECLARE goldValue INT;		/*金币值*/
	DECLARE mineGoldValue INT;	/*矿山币值*/
	DECLARE minGold INT;		/*最小金币值*/
	DECLARE maxGold INT;		/*最大金币值*/
	DECLARE mineGold INT;		/*矿山币*/
	DECLARE mineGoldProbability INT;	/*矿山币刷出机率*/
	
	DECLARE rewardId BIGINT;		/*奖励记录ID*/	
		
	/*定义错误*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION  ROLLBACK;
	
	SELECT i.vipGrade INTO vipGrade FROM t_player_info i WHERE i.playerId = pId; /*获取玩家VIP等级*/
	
	
	START TRANSACTION; /*开始事务*/
	
	/*删除旧数据*/	
	DELETE FROM t_mine_battle_reward WHERE playerId = pId AND stage = stageIndex;
	
	/*获取关卡配置信息*/
	SELECT s.minGold, s.maxGold, s.mineGold, s.mineGoldProbability INTO minGold, maxGold, mineGold, mineGoldProbability FROM t_config_mine_stage s WHERE s.stage = stageIndex; 		
	
	/*计算金币*/
	SET goldValue = FLOOR((RAND() * (maxGold - minGold + 1))  + minGold);
	/*矿山币概率*/
	SET randomValue = FLOOR((RAND() * 1000)) + 1;
	IF randomValue <= mineGoldProbability THEN 
		SET mineGoldValue = mineGold;
	ELSE 
		SET mineGoldValue = 0;
	END IF;
	
	/*插入金币、矿山币收获*/
	INSERT INTO t_mine_battle_reward(playerId, stage, gold, mineGold) VALUES (pId, stageIndex, goldValue, mineGoldValue);
	SELECT MAX(id) INTO rewardId FROM t_mine_battle_reward WHERE playerId = pId AND stage = stageIndex; /*获得记录ID*/
	/*计算英雄*/
	BEGIN	
		DECLARE minHeroId INT;		/*最小英雄ID*/
		DECLARE maxHeroId INT;		/*最大英雄ID*/
		DECLARE probability INT;	/*机率*/
		DECLARE heroId INT;		/*英雄ID*/
		DECLARE heroStar INT;		/*英雄星级*/
		DECLARE heroCursorDone INT DEFAULT 0; /*英雄游标完成*/
		DECLARE heroCursor CURSOR FOR SELECT t.minHeroId, t.maxHeroId, t.probability, t.star FROM t_config_mine_stage_reward_hero t WHERE t.stage = stageIndex ORDER BY t.probability ASC; 
		
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET heroCursorDone = 1;
		
		OPEN heroCursor;  
			cursor_loop: LOOP  
			
				FETCH heroCursor INTO minHeroId, maxHeroId, probability, heroStar;
				/*数据已取完*/
				IF heroCursorDone = 1 THEN  
					LEAVE cursor_loop;  
				END IF;
				
				SET randomValue = FLOOR((RAND() * 1000)) + 1;
				IF(randomValue <= probability) THEN /*抽中*/
					/*排除掉术士小弟的英雄编号*/
					SELECT identifier INTO heroId FROM t_hero WHERE initialStar = heroStar AND identifier <> 104 AND identifier >= minHeroId AND identifier <= maxHeroId ORDER BY RAND() LIMIT 0, 1;					
					/*插入数据*/
					INSERT INTO t_mine_battle_reward_hero(heroId, star, rewardId) VALUES(heroId, heroStar, rewardId);		
					
					SET countFlag = countFlag + 1;
					IF(countFlag >= maxRewardItemCount) THEN /*奖励数量已满*/
						LEAVE cursor_loop;  
					END IF;
				END IF;
			
			END LOOP cursor_loop;
		CLOSE heroCursor; 
	END;
	
	/*计算物品*/
	IF(countFlag < maxRewardItemCount) THEN
		BEGIN			
			DECLARE configId BIGINT;	/*配置ID*/
			DECLARE minItemId INT;		/*最小物品ID*/
			DECLARE maxItemId INT;		/*最大物品ID*/
			DECLARE probability INT;	/*机率*/
			DECLARE itemId INT;		/*物品ID*/
			DECLARE amount INT;		/*数量*/
			DECLARE itemCursorDone INT DEFAULT 0; /*英雄游标完成*/
			DECLARE itemCursor CURSOR FOR SELECT t.id, t.minItemId, t.maxItemId, t.probability FROM t_config_mine_stage_reward t WHERE t.stage = stageIndex ORDER BY t.probability ASC; 
			
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET itemCursorDone = 1;
			
			OPEN itemCursor;  
				cursor_loop: LOOP  
				
					FETCH itemCursor INTO configId, minItemId, maxItemId, probability;
					/*数据已取完*/
					IF itemCursorDone = 1 THEN  
						LEAVE cursor_loop;  
					END IF;
					
					SET randomValue = FLOOR((RAND() * 1000)) + 1;
					
					IF(randomValue <= probability) THEN /*抽中*/
						
						/*获得物品编号*/
						SELECT identifier INTO itemId FROM t_item WHERE identifier >= minItemId AND identifier <= maxItemId ORDER BY RAND() LIMIT 0, 1;
						/*获得数量*/
						SELECT t.amount INTO amount FROM t_config_mine_stage_reward_amount t 
							WHERE t.configId = configId AND minVipGrade <= vipGrade AND maxVipGrade >= vipGrade LIMIT 0, 1;
						
						/*插入数据*/
						INSERT INTO t_mine_battle_reward_item(itemId, amount, rewardId) VALUES(itemId, amount, rewardId);
																	
						SET countFlag = countFlag + 1;
						IF(countFlag >= maxRewardItemCount) THEN /*奖励数量已满*/
							LEAVE cursor_loop;  
						END IF;
					END IF;
				
				END LOOP cursor_loop;
			CLOSE itemCursor;  			
		END;
	END IF;
		
	COMMIT; /*事务提交*/ 
    END$$

DELIMITER ;