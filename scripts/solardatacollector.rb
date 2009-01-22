#!/usr/bin/env ruby

# Takes a years worth of solar data and creates a CSV file from it.
# The data comes from http://www.cmpsolv.com/los/sunsetexp.html

output = File.new("../testdata/solardata.csv", "w")
month = 0

File.open("../testdata/solardata.txt", "r").grep(/(\d)+\s(([0-1][0-9]|[2][0-3]):([0-5][0-9])(\s)?)+/).each do |line|
    line.chop!()
    line.gsub!(/\s(DAY)/, '99:99')
    line.gsub!(/\s{2}/, ' ')
    line.gsub!(/^\s/, '0')
    line.gsub!(/\s{1}/, ',')
    
    #Need a way to grab the day.
    date = line.match(/^(\d\d)/)
    if( date.to_s == '01' )
        month = month + 1
    end
    
    line.gsub!(/^(\d\d)/, '\1/2008')     
    line = month.to_s + '/' + line
    output.puts(line)
end
