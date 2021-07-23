var departmentApi = Vue.resource('/department/{/id}');

Vue.component('department-form', {
    props: ['departments'],
    data: function (){
        return {
            text: ''
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Write something" v-model="text"/>' +
            '<input type="button" value="Save" @click="save">' +
        '</div>',
    methods: {
        save: function () {
            var department = { text: this.text};

            departmentApi.save({}, department).then(result =>
                result.json().then(data => {
                this.departments.push(data);
                })
            )
        }
    }
});

Vue.component('department-row', {
    props: ['department'],
    template: '<div><i>({{ department.id}})</i> {{department.text}} </div>'
})

Vue.component('departments-list', {
    props: ['departments'],
    template: '<div>' +
            '<department-form :departments="departments"/>' +
            '<department-row v-for="department in departments" ' +
            ':key="department.id" :department = "department"></department-row>' +
        '</div>',
    created: function() {
        departmentApi.get().then(result =>
            result.json().then(data =>
                data.forEach(department => this.departments.push(department))
            )
        )
    }
})

var app = new Vue({
    el: '#app',
    template: '<departments-list :departments="departments"/>',
    data: {
        departments: []
    }
})