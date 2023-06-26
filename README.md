# Money Transfer Home Task
Implementation of a RESTful API for creating accounts and money transfers between accounts and also withdrawing to 
external accounts

## Description

This project allows user to send money from one account to another also withdraw money to the external account. When 
money is transferred the account balances are updated accordingly. When money is withdrawed balance is updated 
regardless of the status of the process. In next version we are aiming to add withdrawed amount back to the sender 
account if the withdrawing is failed. We will implement a job which will run periodically and checks failed withdrawals 
and update the accounts

## Getting Started


This project is simple money transfer api. It has the following endpoints  
*[POST]/api/v1/create-account: creates a new account  
*[GET]/api/v1/accounts: returns all existing accounts  
*[POST]/api/v1/send-money: sends money from the sender account to the receiver account  
*[GET]/api/v1/transactions: returns all existing transactions  
*[POST]/api/v1/withdrawal:  withdraws money from the sender account to the external address  
*[GET]/api/v1/withdrawal/$withdrawalId: return status of withdrawal

### Executing program

Here are the steps to run the application
* go inside of project of folder
* run `./gradlew bootRun` command


Porject runs on [localhost:8080](http://localhost:8080)  
[Swagger UI](http://localhost:8080/h2_console)

## Authors

Osman Demir  
+905077490541  
osmgyte@gmail.com  
