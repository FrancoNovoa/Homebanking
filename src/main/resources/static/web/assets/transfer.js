const { createApp } = Vue
  createApp({
    data() {
      return {
        clientes: [],
        cuentas:[],
        transacciones:[],
        queryString:'',
        params: '',
        id:'',
        amount:'',
        description:'',
        accountNumberFrom: '',
        accountNumberTo: '',
        selectorCuentas: '',
      }
    },
    created(){
      this.queryString = location.search
      this.params = new URLSearchParams(this.queryString)
      this.id = this.params.get('id')
      this.todosLosClientes()
      
    },
    methods: {
      fechaNormal(arrayData){
        arrayData.forEach(fechaBroder => {
          fechaBroder.date = fechaBroder.date.slice(0,10)
        })
      },
      todosLosClientes(){
        axios.get("/api/clients/current")
        .then((response) =>{
          this.clientes = response.data;
          this.cuentas = this.clientes.account;
          console.log(this.cuentas);
      })
    },
    logout(){
      axios.post("/api/logout")
      .then(() => {
        window.location.href = '/web/index.html'
      })
    },
    makeTransaction(){
      let accountNumberTo = this.accountNumberTo.toUpperCase();
      const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          confirmButton: 'btn btn-success',
          cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
      })
      
      swalWithBootstrapButtons.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, sure!',
        cancelButtonText: 'No, cancel!',
        reverseButtons: true
      })
      .then((result) => {
        if (result.isConfirmed) {
          axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&accountNumberFrom=${this.accountNumberFrom}&accountNumberTo=${accountNumberTo}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(() => swalWithBootstrapButtons.fire(
            'Amazing!',
            'Your transfer was realized.',
            'success'
          ))
          .then(() => window.location.href = '/web/accounts.html?id=' + this.clientes.id) 
          .catch(err => Swal.fire({
            icon: 'error',
            title: 'Ops...',
            text: `${err.response.data}`}))
        } else if (
          result.dismiss === Swal.DismissReason.cancel
        ) {
          swalWithBootstrapButtons.fire(
            'Cancelled',
            'Your transaction was not carried out',
            'error'
          )
        }
      })
    },
  },
    computed: {},
  }).mount('#app')