# InitializeAuctionUI
This is the initialize auction UI service for my FYP. It is written in Java. It uses a 0mq binding and also connects to
Firebase. It is responsible for initializing the UI on the mobile app when the auction starts.

## Project Setup

Requires CouchDB running on port 5984 with the correct database and collection.

Database: auction_info

Sample Item JSON: {
    "_id": "1",
    "name": "HP Omen",
    "image": "images/omen/omen1.jpg",
    "starting_bid": 2000
  }

## License

None

## Setting up InitializeAuctionUI service on AWS

- Created AWS EC2 Ubuntu instance
- Connected to instance using FileZilla using Public DNS and .pem keyfile
- Upload JAR folder containing JAR file and properties folder to Server
- Connected to server instance using PuTTy using ec2-user@PublicDNS and .ppk keyfile for SSH Auth

Created and inserted sample objects:

    curl -i -X POST "http://localhost:5984/auction_info/" \
	-H "Content-Type: application/json" \
	-d '{"_id": "1", "name": "HP Omen", "image": "images/omen/omen1.jpg", "starting_bid": 2000}'

	curl -i -X POST "http://localhost:5984/auction_info/" \
	-H "Content-Type: application/json" \
	-d '{"_id": "2", "name": "iPhone 6", "image": "images/iPhone6/iphone1.jpg",
	"starting_bid": 1000}'

## Application Setup Required

- Installed Java 8 -> http://stackoverflow.com/questions/16263556/installing-java-7-on-ubuntu
	  sudo add-apt-repository ppa:webupd8team/java
	  sudo apt-get update
	  sudo apt-get install oracle-java8-installer
	  sudo apt-get install oracle-java8-set-default
- Installed CouchDB -> https://www.digitalocean.com/community/tutorials/how-to-install-couchdb-and-futon-on-ubuntu-12-04
- Created CouchDB database called 'auction_info'
    sudo apt-get update
	  sudo apt-get install couchdb
	  curl localhost:5984
	  curl -X PUT localhost:5984/auction_info

Running the service -> java -jar InitializeAuctionUI_jar/InitializeAuctionUI.jar

Service runs and works as expected
