USE [ICCARD]
GO
/****** Object:  StoredProcedure [dbo].[SEND_CREATE_BATCH_BY_ACARD]    Script Date: 01/15/2014 11:11:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[SEND_CREATE_BATCH_BY_ACARD]
AS

BEGIN TRY
	BEGIN TRANSACTION;
	
	--alter table SEND_ACARD add temp_batch_id bigint;

DELETE FROM SEND_ACARD_TEMP_BATCH;
	
	--根据acard数据创建临时批次
select
	*
into SEND_ACARD_TEMP_BATCH
from (
	select 
		--IDENTITY(bigint, 1, 1) as ID,
		row_number() over(order by  rand() ) as ID,
		sa.batchcode as CARD_BATCH_NO, 
		sa.countyname as DISTRICT_NAME, sa.countycode as DISTRICT_CODE, 
		sa.townname as TOWN_NAME, sa.towncode as TOWN_CODE, 
		sa.villagename as VILLAGE_NAME, sa.villagecode as VILLAGE_CODE,
		COUNT(*) as CARD_COUNT,
		'CITY_RECEIVE' AS STATUS,
		'VILLAGE' as BATCH_TYPE,
		NULL AS CREATE_STATUS
	from 
		send_acard sa
	where 
		sa.cardtype='3'
	group by
		sa.batchcode, sa.countyname, sa.countycode, sa.townname, sa.towncode, sa.villagename, sa.villagecode
) batch_temp;

alter table SEND_ACARD_TEMP_BATCH alter column CREATE_STATUS varchar(20);

----更新a卡的新增的临时批次id
--select satb.id, sa.* 
update sa set sa.temp_batch_id=satb.id
from SEND_ACARD sa
left join dbo.SEND_ACARD_TEMP_BATCH satb
on (
	sa.batchcode=satb.CARD_BATCH_NO and 
	sa.countyname=satb.DISTRICT_NAME and
	sa.countycode=satb.DISTRICT_CODE and
	sa.townname=satb.town_name and
	sa.towncode=satb.town_code and 
	sa.villagename=satb.village_name and
	sa.villagecode=satb.village_code
)
where sa.cardtype='3';

COMMIT TRANSACTION;	
   
END TRY
BEGIN CATCH 
	ROLLBACK TRANSACTION;
	PRINT ERROR_MESSAGE();
	PRINT ERROR_STATE();
	PRINT ERROR_LINE();
	RETURN -1;  
END CATCH
