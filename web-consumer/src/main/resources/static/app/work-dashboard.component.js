(function () {
	class WorkDashboardController {
		constructor($http) {
			this.$http = $http;
		}
		
		$onInit() {
			this.resetForm();
			this.refreshTasks();
		}
		
		refreshTasks() {
			this.$http
				.get('/tasks')
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
		
		refreshTask(id) {
			this.$http
				.get('/tasks/' + id)
				.then(response => {
					for (let i = 0; i < this.tasks.length; i += 1) {
						if (this.tasks[i].id === id) {
							return this.tasks[i] = response.data;
						}
					}
				});
		}
		
		resetForm() {
			this.taskName = null;
			this.amount = null;
			this.description = null;
			this.taskType = null;
		}
		
		makeTask() {
			this.$http
				.post('/tasks', {
					name: this.taskName,
					amount: this.amount,
					description: this.description,
					type: this.taskType
				})
				.then(() => {
					this.resetForm();
					this.refreshTasks();
				});
		}
		
		isValid() {
			return !!(this.taskName && this.amount && this.description && this.taskType);
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
		.component('workDashboard', {
			templateUrl: `app/work-dashboard.component.html`,
			controllerAs: `dashboard`,
			controller: ['$http', ($http) => new WorkDashboardController($http)]
		});
})();
