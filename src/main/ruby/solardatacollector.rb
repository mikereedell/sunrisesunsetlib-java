 #
 # Copyright 2008-2009 Mike Reedell / LuckyCatLabs.
 # 
 # Licensed under the Apache License, Version 2.0 (the "License");
 # you may not use this file except in compliance with the License.
 # You may obtain a copy of the License at
 # 
 #      http://www.apache.org/licenses/LICENSE-2.0
 # 
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS,
 # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 # See the License for the specific language governing permissions and
 # limitations under the License.


#!/usr/bin/env ruby

# Takes a years worth of solar data and creates a CSV file from it.
# The data comes from http://www.cmpsolv.com/los/sunsetexp.html

output = File.new("../../../testdata/solardata.csv", "w")
month = 0

File.open("../../../testdata/solardata.txt", "r").grep(/(\d)+\s(([0-1][0-9]|[2][0-3]):([0-5][0-9])(\s)?)+/).each do |line|
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
