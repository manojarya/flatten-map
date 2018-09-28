# flatten-map

Motivation behind this small tool lies in eternal document based vs relational databases fights.
There are certain situations where you have nested maps and you want them flat. Like a relational table.
This tool does exactly that. To carve out relational tables kind of structures out of nested maps.

## Usage

Flatten a nested map m into rows for the given keys. Keys can have values for both a straight map look up or for collection values.

Nested path is separated by dot (.) while collection paths are separated by dollar ($).

Returns collection of maps as if a relational table format.

usage: 

![alt text](https://github.com/manojarya/flatten-map/blob/master/test/usage-1.png)

## License

Copyright © 2018

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.