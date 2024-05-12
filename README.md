## About

A fully developed MVP application with CRUD operations for board game lovers who want to leave a review. Utilises Board Game Geek's API allowing users to search through over 1000 games.
DB comes with existing USER and ADMIN (moderator) accounts but if user wishes, they can create their own USER account.
In the future users would be able to create events, allowing them to set how many people can attend, their location and date/times.
This application uses MySQL to run the DB, Java for the server side and React to run the application. 

<img width="1440" alt="Screenshot 2024-05-12 at 15 54 47" src="https://github.com/mxangelo36r/good-games/assets/138813288/820bbc1c-f25d-4cb1-8d4c-b8bddc16e767">

## Getting Started

To get a local copy up and running follow these simple example steps.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/mxangelo36r/good-games.git
   ```
   
2. Run Docker and run the following SQL file: `server/sql/goodgames-schema-prod.sql`
   
3. Configure `server/src/main/java/learn/goodgames/App.java` in your IDE to your specific MySQL username and password using the following properties: `GOOD_GAMES_DB_USERNAME` & `GOOD_GAMES_DB_PASSWORD`
   
4. Cd into `client/good-games-ui` and install NPM packages
   ```sh
   npm install
   ```
   
5. Run the project in `client/good-games-ui`
   ```sh
   npm start
   ```
