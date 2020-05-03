# 3.5.1
* Table Of Contents: Now you can see a table of contents at the editor when previewing
* Code block button: Click on code block button while editing (selecting a existent code or not) to generate a new code
 block
* Minor bug fixes 
# 3.4.1
* Fixing pages URLs on sitemap api

# 3.4.0
* Configuring robots and sitemaps 

# 3.3.1
* Fixing analytics ID wasn't being replicated to pages 

# 3.3.0
* Fixing tables format on public view

# 3.2.0
* Upgrading micronaut
* Upgrading graalvm 
* Fixing missing appname on public pages

# 3.1.0
* Switching docker images to the ones which use less resources

# 3.0.8
* Fixing sunec dependency

# 3.0.0
* Migrating to monolith first architecture 
* Migrating to Java + Native Image + Micronaut
* Setup Postgres support

# 2.12.6
* fixing highlight in public view

# 2.12.5
* Fixing statics not found

# 2.12.0
* Create a config panel to turn code block size configurable for mobile and desktop
* Make code block collapsible
* Tollbar must be fixed in mobile
* Codeblock size now is configurable in settings page `Left top menu -> settings`

# 2.11.4
* Fix preview link not opening in new tab after click in preview two times

# 2.11.3
* Fix code scroll not working

# 2.11.2
* Fixing save button locks forever when bookmark is created
* Updating readme

# 2.11.1
* WEB - Remove bootstrap modal, now page must open in fullscreen like another page 
* When saving a public bookmark show a button to it public link
* Prevent save double click
* New Look and feel

# 2.10.1
* Initial results replace search results
* Format blockquote like StackOverflow
* Prevent page reload when type enter in search
* Disabling auto search, now you must to type enter after type your search
* Prevent save double click

# 2.10.0
* Format paragraph and lists justified
* Mobile Word/Wrap Button

# 2.9.2
* Fix container stop taking forever

# 2.9.1
* Fixing HTML not rendering when code in plain mode

# 2.9.0
* Prevent user from accidentally get out of creation/edition page

# 2.8.2
* Using gzip for all plain text URLs (only for the site, at nginx level)
* Moving bookmark search  apis to the bk-api project
* Reduce the bookmark description to 160 characters  in search API
* Disabling code auto-syntax detection to increase page parse
* fixing first deploy database build

# 2.2.4
* Get bookmark link when it turn public
* Code format in public vision now it's like the private
* Highlight increase
* Mobile screen experience
