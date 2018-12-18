/**
 * 
 */
var m ={};

for(var i = 0; i< 100;i++)
{
	list.add('v' + i);
	print( i);
	m['v' + i] = "val:" + i;
}

print( list.size());


for(var p in m)
{
	print( m[p]);
}

var z = map.x * map.y;
print("z= " + z);