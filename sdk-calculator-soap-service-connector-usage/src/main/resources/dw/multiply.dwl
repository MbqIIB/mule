%dw 2.0
output text/xml encoding = 'UTF-8'
ns soap http://schemas.xmlsoap.org/soap/envelope/
ns xsi http://www.w3.org/2001/XMLSchema-instance 
ns xsd http://www.w3.org/2001/XMLSchema
ns n http://tempuri.org/
---
{
	soap#Envelope @('xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance", 'xmlns:xsd': "http://www.w3.org/2001/XMLSchema"): {
		soap#Body: {
			Multiply @(xmlns: "http://tempuri.org/"): {
				intA: payload.intA,
				intB: payload.intB
			}
		}
	}
}