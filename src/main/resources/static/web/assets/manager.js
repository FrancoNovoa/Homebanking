const { createApp } = Vue
  createApp({
    data() {
      return {
        clients:[],
        name: '',
        lastName: '',
        email:'',
        queryString:'',
        params: '',
        id:'',
      }
    },
    created(){
      this.queryString = location.search
      this.params = new URLSearchParams(this.queryString)
      this.id = this.params.get('id')
      this.loadData()
    },
    methods: {
      loadData(){
        axios.get("/api/clients")
        .then((response) =>{
          this.clients = response.data;
        })
      },
      logout(){
        axios.post("/api/logout")
        .then(() => {
          window.location.href = '/web/index.html'
        })
      },
      addClient(){
        axios.post("/rest/clients",{
          name: this.name,
          lastName: this.lastName,
          email: this.email,
        })
        .then(respuesta =>{
          this.clients.push(respuesta.data)
          this.loadData()
        })
      },
      deleteClient(clientSelect){
        axios.delete("/rest/clients/"+ clientSelect.id)
        .then(() =>{
          this.loadData()
        })
      },
      editClient(clientSelect){
        let newEmail 
        newEmail = prompt('Enter your new email')
        let newName 
        newName= prompt('Enter your new name')
        let newLast
        newLast = prompt('Enter your new last name')
        client ={
          name: newName,
          lastName: newLast,
          email: newEmail,
        }
        axios.patch("/rest/clients/"+ clientSelect.id, client)
        .then(() =>{
          this.loadData()})
      },
    },
    computed: {},
  }).mount('#app')