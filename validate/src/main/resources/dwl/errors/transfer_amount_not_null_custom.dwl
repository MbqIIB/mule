%dw 2.0
output application/json
var today = now()
---
{
		Error_Code: 2204,
		Type: error.errorType.namespace ++ '.' ++ error.errorType.identifier,
		Error_Description: error.description,
		timestamp: today		
}