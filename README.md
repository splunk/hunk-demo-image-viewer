# images
This images project is a webservice for serving pictures stored in hadoop to a Splunk UI.

# Installation
Put resources/public/image_search_alpha_html.html in some Splunk local/data/ui/html and it'll be visible as a view in that app.

# Rough edges:
- Hadoop has to be run on localhost:9000 right now
- Hadoop images has to be stored in a file matching *.tgz or *.map/data, where *.map is a directory with a file named 'data'

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2013 FIXME
