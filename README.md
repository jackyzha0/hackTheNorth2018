# hackthenorth2018 - Tappit - Cryptopayments over NFC

## Inspiration
NFC (Near field communication) has become increasingly prevalent with the rise of services like Google/Apple Pay. It's clear that NFC is the future when it comes to mobile payments.
However, aside from a couple 'Bitcoin debit cards', there are very few players in the cryptocurrency space that are taking advantage of NFC technology, with most wallets requiring one to scan a QR code or enter in an address manually in order to make a payment. We built Tappit to simplify this payment process for merchant and consumers.

## What it does
Tappit comes in two parts:

An Android app - this is where all the NFC magic happens. When the backs of two phones running the app are pressed together, the app enables the phones to send ethereum to the other in a completely frictionless experience. One's account is kept secure by both their device's unique ID and biometric data from the phone's fingerprint scanner.

A web app for merchants to check simple analytics, graphs, etc. and handle ethereum transactions.

## How we built it
The app was created with Java in Android Studio. The web app and api backend are done in node.js.

## Challenges we ran into
This was our first time working with NFC and we underestimated how difficult it would be in the beginning. Thankfully we were able to figure everything out in a (relatively) timely manner.

Also, HTTP requests in Java were surprisingly frustrating. The Java library left a lot to be desired, especially compared to the Python/Javascript libraries we're used to.

## Accomplishments that we're proud of
We're happy that we were able to learn a few new technologies and ship a prototype in just a day and a half. We had a slow start due to NFC's somewhat steep learning curve but we were able to learn quickly and pick up the pace as the hackathon progressed.

## What we learned
We learned a ton about the inner workings of cryptocurrency and NFC. We also learned how to utilize the fingerprint scanner in the Android SDK.

## What's next for Tappit - Crypto payments over NFC
As of now, our project is a great proof-of-concept but it has limited commercial viability. Over the next little while we hope to expand Tappit's feature set and make it a more robust solution that can be used in a real-world setting. We believe that Tappit greatly improves the user experience when it comes to retail cryptocurrency payments, and we hope to get a chance to deploy it 'IRL'.
