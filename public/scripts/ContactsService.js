function ContactService($resource) {
	  return $resource('contacts',{}, 
			  {'getAll':  {method:'GET', isArray:true}}
	  );
}