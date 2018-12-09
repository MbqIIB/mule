%dw 2.0
output application/json
var today = now()
---
if (vars.tranfer_flag == false)
	{
		Error_Code: 500,
		Type: "TRANFER.VALIDATION_ERROR",
		Error_Description: "Fund " ++ vars.amount ++
			" can not be transferred from account " 
			++ payload.account_from ++ " to account " 
			++  payload.account_to,
		timestamp: today
	}
else if (vars.transffer_limit <= vars.amount)
	{
		Error_Code: 600,
		Type: "TRANFSER.EXCEEDS_LIMIT",
		Error_Description: "Fund " ++ vars.amount 
			++ " can not be transferred from account " 
			++ payload.account_from ++ " to account " 
			++  payload.account_to
			++ ". Due transfer amount exceeds transfer limit "
			++ vars.transffer_limit,
		timestamp: today
	
	}
else 
	{
		Message_Code: 200,
		Type: "TRANSFER.SUCCESS",
		Description: "Fund " ++ vars.amount ++
			" has been transferred from account " 
			++ payload.account_from ++ " to account " 
			++  payload.account_to,
		timestamp: today
	
	}