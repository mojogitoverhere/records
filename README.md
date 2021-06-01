# Records Application
This application includes a command line tool and a web
server to ingest and display user records.

## Building the Code
To build both the command line tool and the web server, run
```
mvn clean install
```

## Using the Command Line
A text file of records can be ingested and the records will be
displayed in three different forms:
1. sorted by ascending favorite color then ascending last name
2. sorted by ascending date of birth
3. sorted by descending last name

Run the command line tool with the following command. Multiple
records files can be ingested at the same time, even if each one
is using a different format
```
java -cp target/records-1.0-SNAPSHOT.jar com.david.records.RecordsCommand <records-file> ...
```

Each records file must consist of one record per line,
where each record has 5 fields (lastname, firstname,
 email, favorite color, and date of birth) and each field
 can be separated by one of three characters:
- A pipe:
```
Minelli|Alister|aminelli2@webs.com|Violet|6/24/1990
```
- A comma:
```
Minelli,Alister,aminelli2@webs.com,Violet,6/24/1990
```
- Or a space:
```
Minelli Alister aminelli2@webs.com Violet 6/24/1990
```

### Example Output
```
OUTPUT 1: Sorted by ascending favorite color then last name
LAST NAME|FIRST NAME|EMAIL                  |FAVORITE COLOR|DATE OF BIRTH|
--------------------------------------------------------------------------
Attersoll|Gail      |gattersoll0@foxnews.com|Blue          |12/1/2019    |
Mansion  |Salomone  |smansion4@state.gov    |Fuscia        |2/7/1977     |
Hastings |Lynnet    |lhastings5@fotki.com   |Indigo        |11/29/2015   |
Latter   |Hanan     |hlatter3@skyrock.com   |Mauv          |10/2/1994    |
Tarte    |Sella     |starte9@instagram.com  |Orange        |6/17/2004    |
Torrans  |Ralf      |rtorrans7@newsvine.com |Purple        |7/16/2001    |
Chisholm |Deck      |dchisholm6@google.it   |Teal          |9/20/1980    |
Shalders |Pippy     |pshalders8@adobe.com   |Turquoise     |3/27/1987    |
Minelli  |Alister   |aminelli2@webs.com     |Violet        |6/24/1990    |
Seczyk   |Michaeline|mseczyk1@usgs.gov      |Yellow        |3/4/1998     |

OUTPUT 2: Sorted by ascending date of birth
LAST NAME|FIRST NAME|EMAIL                  |FAVORITE COLOR|DATE OF BIRTH|
--------------------------------------------------------------------------
Mansion  |Salomone  |smansion4@state.gov    |Fuscia        |2/7/1977     |
Chisholm |Deck      |dchisholm6@google.it   |Teal          |9/20/1980    |
Shalders |Pippy     |pshalders8@adobe.com   |Turquoise     |3/27/1987    |
Minelli  |Alister   |aminelli2@webs.com     |Violet        |6/24/1990    |
Latter   |Hanan     |hlatter3@skyrock.com   |Mauv          |10/2/1994    |
Seczyk   |Michaeline|mseczyk1@usgs.gov      |Yellow        |3/4/1998     |
Torrans  |Ralf      |rtorrans7@newsvine.com |Purple        |7/16/2001    |
Tarte    |Sella     |starte9@instagram.com  |Orange        |6/17/2004    |
Hastings |Lynnet    |lhastings5@fotki.com   |Indigo        |11/29/2015   |
Attersoll|Gail      |gattersoll0@foxnews.com|Blue          |12/1/2019    |

OUTPUT 3: Sorted by descending last name
LAST NAME|FIRST NAME|EMAIL                  |FAVORITE COLOR|DATE OF BIRTH|
--------------------------------------------------------------------------
Torrans  |Ralf      |rtorrans7@newsvine.com |Purple        |7/16/2001    |
Tarte    |Sella     |starte9@instagram.com  |Orange        |6/17/2004    |
Shalders |Pippy     |pshalders8@adobe.com   |Turquoise     |3/27/1987    |
Seczyk   |Michaeline|mseczyk1@usgs.gov      |Yellow        |3/4/1998     |
Minelli  |Alister   |aminelli2@webs.com     |Violet        |6/24/1990    |
Mansion  |Salomone  |smansion4@state.gov    |Fuscia        |2/7/1977     |
Latter   |Hanan     |hlatter3@skyrock.com   |Mauv          |10/2/1994    |
Hastings |Lynnet    |lhastings5@fotki.com   |Indigo        |11/29/2015   |
Chisholm |Deck      |dchisholm6@google.it   |Teal          |9/20/1980    |
Attersoll|Gail      |gattersoll0@foxnews.com|Blue          |12/1/2019    |
```

## Running the Web Server
Start the web server by running the following command.
The web server listens on port 7000.
```
java -cp target/records-1.0-SNAPSHOT.jar com.david.records.RecordsApplication
```

Records can be ingested one at a time by doing a POST request
on the `/records` endpoint. The endpoint accepts the same record formats
as the command line tool.

Records can be queried by hitting the `/records/color`, `/records/birthdate`,
or `/records/name` endpoints, which return results ordered by favorite color,
date of birth, or last name, respectively. Results are returned in JSON format
and include each record as well as the total record count.

### Example Query Response
```
{
    "records": [
        {
            "firstName": "Raddy",
            "lastName": "Lawland",
            "email": "rlawland15@census.gov",
            "favoriteColor": "Yellow",
            "dateOfBirth": "5/26/2020"
        },
        {
            "firstName": "Forest",
            "lastName": "McCarlie",
            "email": "fmccarlie18@yahoo.co.jp",
            "favoriteColor": "Aquamarine",
            "dateOfBirth": "3/12/2019"
        },
        {
            "firstName": "Alister",
            "lastName": "Minelli",
            "email": "aminelli2@webs.com",
            "favoriteColor": "Red",
            "dateOfBirth": "6/24/1990"
        }
    ],
    "total": 3
}
```
