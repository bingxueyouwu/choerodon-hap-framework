UPDATE SYS_CODE_VALUE_B
SET PARENT_CODE_VALUE = (
    SELECT T.VALUE FROM (SELECT * FROM SYS_CODE_VALUE_B ) T WHERE SYS_CODE_VALUE_B.PARENT_CODE_VALUE_ID = T.CODE_VALUE_ID
)
WHERE SYS_CODE_VALUE_B.PARENT_CODE_VALUE_ID IS NOT NULL