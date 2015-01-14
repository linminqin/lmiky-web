/*==============================================================*/
/* Table: t_account                                             */
/*==============================================================*/
create table t_account
(
   id                   bigint not null auto_increment comment 'ID',
   loginName            varchar(64) not null comment '登陆账号',
   pwd                  varchar(64) not null comment '密码',
   registerTime         datetime not null default CURRENT_TIMESTAMP comment '注册时间',
   state                int not null default 0 comment '状态：0=正常，1=禁用',
   primary key (id)
);

alter table t_account comment '账号';

/*==============================================================*/
/* Index: idx_account_loginname                                 */
/*==============================================================*/
create unique index idx_account_loginname on t_account
(
   loginName
);

/*==============================================================*/
/* Table: t_account_token                                       */
/*==============================================================*/
create table t_account_token
(
   id                   bigint not null auto_increment comment 'ID',
   token                varchar(128) not null comment '令牌',
   accountId            bigint not null comment '账号',
   gameZoneId           bigint comment '游戏分区',
   createTime           datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   state                int not null default 0 comment '状态：0=正常，1=禁用',
   primary key (id)
);

alter table t_account_token comment '账号访问令牌';

/*==============================================================*/
/* Index: idx_accounttoken_token                                */
/*==============================================================*/
create unique index idx_accounttoken_token on t_account_token
(
   token
);

/*==============================================================*/
/* Table: t_game_zone                                           */
/*==============================================================*/
create table t_game_zone
(
   id                   bigint not null auto_increment comment 'ID',
   name                 varchar(64) not null comment '名称',
   identifier           varchar(64) comment '编号',
   createTime           datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   domain               varchar(128) not null comment '访问域名',
   sort                 int not null default 0 comment '排序：从大到小排序',
   primary key (id)
);

alter table t_game_zone comment '游戏分区';

/*==============================================================*/
/* Index: idx_gamezone_identifier                               */
/*==============================================================*/
create unique index idx_gamezone_identifier on t_game_zone
(
   identifier
);

/*==============================================================*/
/* Table: t_hero_experience                                     */
/*==============================================================*/
create table t_hero_experience
(
   id                   bigint not null auto_increment comment 'ID',
   grade                int not null comment '级别',
   experience           int not null comment '经验值',
   primary key (id),
   unique key uk_heroexperience_grade (grade)
);

alter table t_hero_experience comment '英雄等级经验表';

/*==============================================================*/
/* Table: t_item_bag                                            */
/*==============================================================*/
create table t_item_bag
(
   id                   bigint not null auto_increment comment 'ID',
   playerId             bigint not null comment '玩家',
   itemId               bigint not null comment '物品ID',
   amount               int not null default 0 comment '数量',
   haveTime             datetime not null default CURRENT_TIMESTAMP comment '拥有时间',
   primary key (id)
);

alter table t_item_bag comment '物品背包';

/*==============================================================*/
/* Index: idx_itembag_player_item                               */
/*==============================================================*/
create unique index idx_itembag_player_item on t_item_bag
(
   playerId,
   itemId
);

/*==============================================================*/
/* Table: t_player                                              */
/*==============================================================*/
create table t_player
(
   id                   bigint not null auto_increment comment 'ID',
   accountId            bigint not null comment '所属账号',
   gameZoneId           bigint not null comment '所属游戏分区',
   nickname             varchar(64) not null comment '昵称',
   portrait             bigint not null comment '头像',
   portraitBackground   bigint comment '头像背景框',
   createTime           datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   state                int not null default 0 comment '状态：0=正常，1=删档，2=禁用',
   primary key (id)
);

alter table t_player comment '玩家';

/*==============================================================*/
/* Index: idx_player_nickname                                   */
/*==============================================================*/
create unique index idx_player_nickname on t_player
(
   nickname
);

/*==============================================================*/
/* Index: idx_player_account_gamezone                           */
/*==============================================================*/
create unique index idx_player_account_gamezone on t_player
(
   accountId,
   gameZoneId
);

/*==============================================================*/
/* Table: t_player_experience                                   */
/*==============================================================*/
create table t_player_experience
(
   id                   bigint not null auto_increment comment 'ID',
   grade                int not null comment '级别',
   experience           int not null comment '经验值',
   primary key (id),
   unique key uk_playerexperience_grade (grade)
);

alter table t_player_experience comment '玩家等级经验表';

/*==============================================================*/
/* Table: t_player_hero                                         */
/*==============================================================*/
create table t_player_hero
(
   id                   bigint not null auto_increment comment 'ID',
   playerId             bigint not null comment '玩家',
   heroId               bigint not null comment '英雄',
   haveTime             datetime not null default CURRENT_TIMESTAMP comment '拥有时间',
   grade                int not null default 1 comment '英雄等级',
   experience           int not null default 0 comment '英雄经验',
   equip1               bigint comment '装备1',
   equip2               bigint comment '装备2',
   equip3               bigint comment '装备3',
   equip4               bigint comment '装备4',
   equip5               bigint comment '装备5',
   equip6               bigint comment '装备6',
   equipGrade1          int default 0 comment '装备1等级',
   equipGrade2          int default 0 comment '装备2等级',
   equipGrade3          int default 0 comment '装备3等级',
   equipGrade4          int default 0 comment '装备4等级',
   equipGrade5          int default 0 comment '装备5等级',
   equipGrade6          int default 0 comment '装备6等级',
   equipExperience1     int default 0 comment '装备1等级经验',
   equipExperience2     int default 0 comment '装备2等级经验',
   equipExperience3     int default 0 comment '装备3等级经验',
   equipExperience4     int default 0 comment '装备4等级经验',
   equipExperience5     int default 0 comment '装备5等级经验',
   equipExperience6     int default 0 comment '装备6等级经验',
   skillGrade1          int not null default 0 comment '如果为0级，则表示不可用',
   skillGrade2          int not null default 0 comment '如果为0级，则表示不可用',
   skillGrade3          int not null default 0 comment '如果为0级，则表示不可用',
   skillGrade4          int not null default 0 comment '如果为0级，则表示不可用',
   growGrade            int not null default 0 comment '进阶等级',
   starGrade            int not null default 1 comment '星级',
   primary key (id)
);

alter table t_player_hero comment '玩家所拥有的英雄列表';

/*==============================================================*/
/* Index: idx_playerhero_player                                 */
/*==============================================================*/
create index idx_playerhero_player on t_player_hero
(
   playerId
);

/*==============================================================*/
/* Index: idx_playerhero_player_hero                            */
/*==============================================================*/
create unique index idx_playerhero_player_hero on t_player_hero
(
   playerId,
   heroId
);

/*==============================================================*/
/* Table: t_player_info                                         */
/*==============================================================*/
create table t_player_info
(
   id                   bigint not null auto_increment comment 'ID',
   playerId             bigint not null comment '所属玩家',
   glod                 int not null default 0 comment '金币',
   diamond              int not null default 0 comment '砖石',
   power                int not null default 0 comment '行动值',
   grade                int not null default 1 comment '玩家等级',
   experience           int not null default 0 comment '玩家经验',
   vipGrade             int not null default 0 comment 'VIP等级',
   vipExperience        int not null default 0 comment 'VIP经验',
   sweepTicket          int not null default 0 comment '扫荡券',
   skillCount           int not null default 0 comment '技能点数',
   primary key (id)
);

alter table t_player_info comment '玩家信息';

/*==============================================================*/
/* Index: idx_playerinfo_playerid                               */
/*==============================================================*/
create unique index idx_playerinfo_playerid on t_player_info
(
   playerId
);

/*==============================================================*/
/* Table: t_player_sign                                         */
/*==============================================================*/
create table t_player_sign
(
   ID                   char(10),
   签到时间                 char(10),
   签到玩家                 char(10)
);

alter table t_player_sign comment '玩家签到信息';

/*==============================================================*/
/* Index: idx_playersign_id                                     */
/*==============================================================*/
create index idx_playersign_id on t_player_sign
(
   签到玩家
);

/*==============================================================*/
/* Table: t_player_sign_state                                   */
/*==============================================================*/
create table t_player_sign_state
(
   ID                   char(10),
   连续签到天数               char(10),
   最后更新时间               char(10),
   玩家                   char(10)
);

alter table t_player_sign_state comment '玩家签到状态';

/*==============================================================*/
/* Index: idx_playersignstate_playerid                          */
/*==============================================================*/
create unique index idx_playersignstate_playerid on t_player_sign_state
(
   玩家
);

/*==============================================================*/
/* Table: t_player_team                                         */
/*==============================================================*/
create table t_player_team
(
   id                   bigint not null auto_increment comment 'ID',
   playerId             bigint not null comment '玩家',
   hero1                bigint comment '英雄1',
   hero2                bigint comment '英雄2',
   hero3                bigint comment '英雄3',
   hero4                bigint comment '英雄4',
   hero5                bigint comment '英雄5',
   primary key (id)
);

alter table t_player_team comment '玩家战队';

/*==============================================================*/
/* Index: idx_playerteam_playerid                               */
/*==============================================================*/
create unique index idx_playerteam_playerid on t_player_team
(
   playerId
);

/*==============================================================*/
/* Table: t_stage                                               */
/*==============================================================*/
create table t_stage
(
   id                   bigint not null auto_increment comment 'ID',
   identifier           varchar(64) comment '编号',
   name                 varchar(128) not null comment '名称',
   sort                 int not null default 0 comment '排序',
   primary key (id)
);

alter table t_stage comment '关卡';

/*==============================================================*/
/* Table: t_stage_map                                           */
/*==============================================================*/
create table t_stage_map
(
   id                   bigint not null auto_increment comment 'ID',
   identifier           varchar(64) comment '编号',
   name                 varchar(128) not null comment '名称',
   sort                 int not null default 0 comment '排序',
   stageId              bigint not null comment '所属关卡',
   summary              varchar(1024) comment '描述',
   needGrade            int not null default 1 comment '需要等级',
   beginPower           int not null default 1 comment '开始闯关消耗的体力',
   endPower             int not null default 1 comment '结束闯关消耗的体力',
   primary key (id)
);

alter table t_stage_map comment '关卡地图';

/*==============================================================*/
/* Table: t_stage_through                                       */
/*==============================================================*/
create table t_stage_through
(
   id                   bigint not null auto_increment comment 'ID',
   playerId             bigint not null comment '玩家',
   beginTime            datetime not null comment '开始时间',
   endTime              datetime comment '结束时间',
   result               int not null default 0 comment '闯关结果：0=闯关中，1=失败，2=成功',
   hero1                bigint comment '英雄1',
   hero2                bigint comment '英雄2',
   hero3                bigint comment '英雄3',
   hero4                bigint comment '英雄4',
   hero5                bigint comment '英雄5',
   stageMap             bigint not null comment '关卡地图',
   score                int comment '评分：如果失败，则评分为0',
   sweep                int not null comment '是否使用扫荡券：0=否，1=是',
   primary key (id)
);

alter table t_stage_through comment '玩家闯关记录';

/*==============================================================*/
/* Index: idx_stagethrough_playerid                             */
/*==============================================================*/
create index idx_stagethrough_playerid on t_stage_through
(
   playerId
);

/*==============================================================*/
/* Table: t_stage_through_best                                  */
/*==============================================================*/
create table t_stage_through_best
(
   id                   bigint not null auto_increment comment 'ID',
   stageThroughId       bigint not null comment '闯关记录ID',
   score                int not null default 0 comment '评分',
   stage                bigint not null comment '关卡',
   stageMap             bigint not null comment '关卡地图',
   playerId             bigint not null comment '玩家',
   primary key (id)
);

alter table t_stage_through_best comment '玩家闯关最优记录';

/*==============================================================*/
/* Index: idx_stagethroughbest_playerid                         */
/*==============================================================*/
create index idx_stagethroughbest_playerid on t_stage_through_best
(
   playerId
);

/*==============================================================*/
/* Index: idx_stagethroughbest_stagemap                         */
/*==============================================================*/
create index idx_stagethroughbest_stagemap on t_stage_through_best
(
   stageMap
);

/*==============================================================*/
/* Index: idx_stagethroughbest_playerid_stagemap                */
/*==============================================================*/
create unique index idx_stagethroughbest_playerid_stagemap on t_stage_through_best
(
   stageMap,
   playerId
);

alter table t_account_token add constraint fk_accounttoken_accountid foreign key (accountId)
      references t_account (id) on delete cascade on update restrict;

alter table t_account_token add constraint fk_accounttoken_gamezoneid foreign key (gameZoneId)
      references t_game_zone (id) on delete cascade on update restrict;

alter table t_item_bag add constraint fk_itembag_playerid foreign key (playerId)
      references t_player (id) on delete cascade on update restrict;

alter table t_player add constraint fk_player_accountid foreign key (accountId)
      references t_account (id) on delete cascade on update restrict;

alter table t_player add constraint fk_player_zoneid foreign key (gameZoneId)
      references t_game_zone (id) on delete cascade on update restrict;

alter table t_player_hero add constraint fk_playerhero_playerid foreign key (playerId)
      references t_player (id) on delete cascade on update restrict;

alter table t_player_info add constraint fk_playerinfo_playerid foreign key (playerId)
      references t_player (id) on delete cascade on update restrict;

alter table t_player_team add constraint fk_playerteam_playerid foreign key (playerId)
      references t_player (id) on delete cascade on update restrict;

alter table t_stage_map add constraint fk_stagemap_stageid foreign key (stageId)
      references t_stage (id) on delete cascade on update restrict;
