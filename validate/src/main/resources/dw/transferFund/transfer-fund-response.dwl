%dw 2.0
output application/json
var today = now()
---
if (vars.transfer_limit < payload.amount)
	{
		Error_Code: 600,
		Type: "TRANFSER.EXCEEDS_LIMIT",
		Error_Description: "Fund " ++ payload.amount 
			++ " can not be transferred from account " 
			++ payload.account_from ++ " to account " 
			++  payload.account_to
			++ ". Due transfer amount exceeds transfer limit "
			++ vars.transfer_limit,
		timestamp: today
	
	}
else 
	{
		Message_Code: 200,
		Type: "TRANSFER.SUCCESS",
		Description: "Fund " ++ payload.amount ++
			" has been transferred from account " 
			++ payload.account_from ++ " to account " 
			++  payload.account_to,
		timestamp: today
	
	}