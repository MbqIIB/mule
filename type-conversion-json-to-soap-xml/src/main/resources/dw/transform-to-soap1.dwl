%dw 2.0
output text/xml encoding = 'UTF-8'
ns soap http://www.laxtech.org/2003/18/soap-envelope/
ns t https://www.laxtech.com/2003/18/transaction/
ns m http://www.laxtech.com/2003/18/prices
ns sec http://www.laxtech.com/2003/18/secext
ns mh http://www.laxtech.com/2003/28/messageHeader
---
{
	soap#Envelope: {
		soap#Header: {
			mh#MessageHeader: {
				mh#MarketId: vars.marketId,
				mh#ReqId: "req8934803",
				mh#Action: "GET_PRICE",
				mh#MetaData: {
					mh#MessageId: "marketsensex:" ++ now() ++ "@laxtech.com",
					mh#Timestamp: now()
				},
				mh#App: {
					mh#Provider: "Live",
					mh#Frequency: 30
				}
			},
			t#Transaction: {
				t#Id: 479,
				t#Type: "local"
			},
			sec#Security: {
				sec#Token: "U34ln4350jse"
			}
		},
		soap#Body: {
			m#GetPrice @(Version: 1.0): {
				m#Ticker: {
					m#Detail @(Desc: "HP" , Category: "Technology" , Cap: "Large Cap" , SubCategory: "IT"): {
						m#Id @(format: "small"): payload.tickerId,
						m#Market @(MarketId: "Trade"):payload.company
					}
				}
			}
		}
	}
}