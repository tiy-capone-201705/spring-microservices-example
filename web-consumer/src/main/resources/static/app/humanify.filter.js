(function () {
	function humanify() {
		return function (input) {
			return input.replace(/\_/, ' ');
		}
	}
	
	angular
		.module('app')
		.filter('humanify', humanify);
})();
