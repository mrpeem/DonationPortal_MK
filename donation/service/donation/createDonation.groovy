

def donation = ec.entity.makeValue("donation.Donation")

def builder = new groovy.json.JsonBuilder()
def root = builder {
	createTransactionRequest {
		merchantAuthentication {
			name '45B8fX6rgZ'
			transactionKey '66TErHd2rUv583W3'
		}
		transactionRequest {
			transactionType 'authCaptureTransaction'
			amount context.donation_amount
			payment {
				creditCard {
					cardNumber '5424000000000015'
					expirationDate '2020-12'
					cardCode '999'
				}
			}
			transactionSettings {
				setting {
					settingName 'testRequest'
					settingValue 'false'
				}
			}
		}
	}
}

// POST
def post = new URL("https://test.authorize.net/gateway/transact.dll").openConnection();
def message = '{"message":"this is a message"}'
post.setRequestMethod("POST")
post.setDoOutput(true)
post.setRequestProperty("Content-Type", "application/json")
post.getOutputStream().write(root.getBytes("UTF-8"));
def postRC = post.getResponseCode();
println(postRC);
if(postRC.equals(200)) {
    println(post.getInputStream().getText());
}


donation.setFields(context, true, null, null)
if (!donation.donationId) donation.setSequencedIdPrimary()
donation.create()