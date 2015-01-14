DELIMITER $$

USE `BattleDotaLogicDB`$$

DROP PROCEDURE IF EXISTS `generateMineShop`$$

CREATE DEFINER=`root`@`%` PROCEDURE `generateMineShop`(IN pId BIGINT)
BEGIN
	DECLARE shopItemCount INT DEFAULT 12; /*商品数量*/
	DECLARE playerGrade INT;	/*玩家等级*/
	DECLARE loopIndex INT DEFAULT 1;	/*循环标示*/
	DECLARE countFlag INT DEFAULT 0;	/*计数标示*/
	DECLARE configId BIGINT;	/*配置ID*/
	DECLARE minItemId INT;	/*最小物品ID*/
	DECLARE maxItemId INT;	/*最大物品ID*/
	DECLARE price INT;	/*售价*/
	DECLARE priceType INT;	/*价格计算类别：0=固定值矿山币，1=物品金币售价乘以系数，系数单位为千分之一*/
	DECLARE appearType INT;	/*物品刷出机率类别：0=固定出现，1=按公式计算*/
	DECLARE amount INT;	/*数量：如果值大于0，则表示出现的数量，如果为0，则根据计算公式来计算数量*/
	DECLARE itemId INT;	/*物品ID*/
	DECLARE itemGoldPrice INT;	/*物品金币售价*/
	
	DECLARE randomValue INT DEFAULT 0;	/*随机值*/
	
	DECLARE cursorDone INT DEFAULT 0; /*游标完成*/
	
	
	/*获取固定刷出的物品列表*/
	DECLARE shopItemsCursor CURSOR FOR SELECT s.id, s.minItemId, s.maxItemId, s.price, s.priceType, s.appearType, s.amount FROM t_config_mine_shop s WHERE s.appearType = 0; 
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursorDone = 1; /*如果sqlstate等于02000时，把done设置成1,也就是找不到数据时*/
	
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	
	SELECT grade INTO playerGrade FROM t_player_info i WHERE i.playerId = pId; /*获取玩家等级*/
	
	START TRANSACTION; /*开始事务*/
	/*START TRANSACTION开始事务，autocommit设为0，如果已经有一个事务在运行，则会触发一个隐藏的COMMIT, 本存储过程可能会在刷新商店的时候也调用，所以直接使用SET autocommit = 0*/
	-- set autocommit = 0; 
	
	/*删除旧数据*/	
	DELETE FROM t_mine_shop_item WHERE playerId = pId;
	
	/*循环游标*/
	OPEN shopItemsCursor;  
		cursor_loop: LOOP  
			FETCH shopItemsCursor INTO configId, minItemId, maxItemId, price, priceType, appearType, amount;
			/*数据已取完*/
			IF cursorDone = 1 THEN  
				LEAVE cursor_loop;  
			END IF;  
			
			SET countFlag = countFlag + 1;
				
			/*获取物品*/
			SELECT identifier, sellPrice INTO itemId, itemGoldPrice FROM t_item WHERE identifier >= minItemId AND identifier <= maxItemId ORDER BY RAND() LIMIT 0, 1;
			IF priceType = 1 THEN	/*按金币比例算*/
				SET price = itemGoldPrice * price / 1000;
			END IF;
			IF amount = 0 THEN	/*计算数量*/
				SET randomValue = FLOOR((RAND() * 100)) + 1;
				SELECT a.amount INTO amount FROM t_config_mine_shop_amount a WHERE a.configId = configId 
					AND (SELECT SUM(probability) FROM t_config_mine_shop_amount s WHERE s.amount <= a.amount AND s.configId = a.configId) >= randomValue
				ORDER BY amount ASC LIMIT 0, 1;
			END IF;	
			INSERT INTO t_mine_shop_item(playerId, itemId, TIME, amount, location, mineGold) VALUES (pId, itemId, CURRENT_DATE, amount, countFlag, price);
		END LOOP cursor_loop;
	CLOSE shopItemsCursor;  
	
	/*获取剩余非固定出现的物品*/	
	SET countFlag = countFlag + 1;
	WHILE countFlag <= shopItemCount
	DO
		/*计算概率*/
		SET randomValue = FLOOR((RAND() * 100)) + 1;
		SELECT a.configId INTO configId FROM t_config_mine_shop_appear a 
			WHERE a.minPlayerGrade <= playerGrade AND a.maxPlayerGrade >= playerGrade AND (
				SELECT SUM(coefficient) FROM t_config_mine_shop_appear s 
				WHERE s.minPlayerGrade <= playerGrade AND s.maxPlayerGrade >= playerGrade AND s.configId <= a.configId
			) >= randomValue
		ORDER BY a.configId ASC, a.coefficient ASC LIMIT 0, 1;
		
		/*获取配置*/
		SELECT s.id, s.minItemId, s.maxItemId, s.price, s.priceType, s.appearType, s.amount 
		INTO configId, minItemId, maxItemId, price, priceType, appearType, amount
		FROM t_config_mine_shop s WHERE s.id = configId;
		
		/*获取物品*/
		SELECT identifier, sellPrice INTO itemId, itemGoldPrice FROM t_item i
			WHERE i.identifier >= minItemId AND i.identifier <= maxItemId ORDER BY RAND() LIMIT 0, 1;
		
		IF priceType = 1 THEN	/*按金币比例算*/
			SET price = itemGoldPrice * price / 1000;
		END IF;
		IF amount = 0 THEN	/*计算数量*/
			SET randomValue = FLOOR((RAND() * 100)) + 1;
			SELECT a.amount INTO amount FROM t_config_mine_shop_amount a WHERE a.configId = configId 
				AND (SELECT SUM(probability) FROM t_config_mine_shop_amount s WHERE s.amount <= a.amount AND s.configId = a.configId) >= randomValue
			ORDER BY amount ASC LIMIT 0, 1;
		END IF;	
		INSERT INTO t_mine_shop_item(playerId, itemId, TIME, amount, location, mineGold) VALUES (pId, itemId, CURRENT_DATE, amount, countFlag, price);		
				
		SET countFlag = countFlag + 1;
	END WHILE;
	
	COMMIT; /*事务提交*/
    END$$

DELIMITER ;