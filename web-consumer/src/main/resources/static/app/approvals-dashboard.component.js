(function () {
	class WorkDashboardController {
		constructor($http) {
			this.$http = $http;
		}
		
		$onInit() {
			this.refreshTasks();
		}
		
		refreshTasks() {
			this.$http
				.get('/approvals/unapproved-tasks')
				.then(response => {
					this.tasks = response.data.map(task => {
						task.created = new Date(task.created);
						return task;
					});
				})
				.catch(error => {
					this.error = 'An error occurred while trying to load the tasks!';
				});
		}
		
		approveTask(task) {
			this.$http
				.patch(task.approvalUrl, [{
					"op": "add",
					"path": "/status",
					"value": true
				}])
				.then(response => this.refreshTasks());
		}
		
		get isLoading() {
			return !this.hasError && !this.tasks;
		}
		
		get hasTasks() {
			return !this.isLoading && this.tasks && this.tasks.length;
		}
		
		get hasNoTasks() {
			return !this.isLoading && this.tasks && !this.tasks.length;
		}
		
		get hasError() {
			return this.error;
		}
	}
	
	angular
		.module('app')
		.component('approvalsDashboard', {
			templateUrl: `app/approvals-dashboard.component.html`,
			controllerAs: `dashboard`,
			controller: ['$http', ($http) => new WorkDashboardController($http)]
		});
})();
