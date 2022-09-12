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
        dateFrom: '',
        dateTo: '',
        numberAccount: '',
        cuentasActivas: [],
      }
    },
    created(){
      this.queryString = location.search
      this.params = new URLSearchParams(this.queryString)
      this.id = this.params.get('id')
      this.todasLasCuentas()
      this.todosLosClientes()
      this.loadData();
    },
    methods: {
      todasLasCuentas(){
        axios.get("/api/accounts/" + this.id)
        .then(respuesta => {
          this.cuentas = respuesta.data;
          this.transacciones = this.cuentas.transaction;
          this.fechaNormal(this.transacciones)
        })
      },
      loadData(){
        axios.get("/api/clients/current")
        .then((response) =>{
          this.clientes = response.data;
          this.cuentasActivas = this.clientes.account.filter(active => active.activeAcc)
          console.log(this.cuentasActivas);
        })
    },
      fechaNormal(arrayData){
        arrayData.forEach(fechaBroder => {
          fechaBroder.date = fechaBroder.date.slice(0,10)
        })
      },
      todosLosClientes(){
        axios.get("/api/clients/current")
        .then((response) =>{
          this.clientes = response.data;
      })
    },
    logout(){
      axios.post("/api/logout")
      .then(() => {
        window.location.href = '/web/index.html'
      })
    },
    downloadPDF(){
      this.dateFrom = new Date(this.dateFrom).toISOString();
      this.dateTo = new Date(this.dateTo).toISOString();
      axios.post("/api/transactions/filtered", {fromDate:this.dateFrom, toDate:this.dateTo, accountNumber:this.numberAccount})
      .then(() => 
      Swal.fire({
        position: 'center-center',
        icon: 'success',
        title: 'Your file has been downloaded',
        showConfirmButton: false,
        timer: 1000
      }))
    .then(() => {
      window.location.reload()
    })},
  },
    computed: {},
  }).mount('#app')