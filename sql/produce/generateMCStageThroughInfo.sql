DELIMITER $$

USE `BattleDotaLogicDB`$$

DROP PROCEDURE IF EXISTS `generateMCStageThroughInfo`$$

CREATE DEFINER=`root`@`%` PROCEDURE `generateMCStageThroughInfo`(IN pId BIGINT, IN stageIdentifier INT)
BEGIN		
	
	/*定义错误*/
	DECLARE EXIT HANDLER FOR SQLEXCEPTION  ROLLBACK;
	
	START TRANSACTION; /*开始事务*/
	
	DELETE FROM t_mc_stage_through_info WHERE playerId = pId AND stage = stageIdentifier; /*清空旧数据*/
	
	INSERT INTO t_mc_stage_through_info(playerId, stage, stageMap, score) SELECT pId, stageIdentifier, t.identifier, 0 FROM t_mc_stage_map t WHERE t.stageIdentifier = stageIdentifier;
	
	COMMIT; /*事务提交*/ 
	
    END$$

DELIMITER ;