const { createApp } = Vue
  createApp({
    data() {
      return {
        queryString:'',
        params: '',
        id:'',
        clientes: [],
        cuentas:[],
        prestamos: [],
        loanName:'',
        lasCuotas: '',
        amount: '',
        accountNumberDeposit:'',
        loansPropiertis:[],
        loanCar: {},
        loanPersonal:{},
        loanMort:{},
        total:'',
      }
    },
    created(){
      this.queryString = location.search
      this.params = new URLSearchParams(this.queryString)
      this.id = this.params.get('id')
      this.todosLosClientes()
      this.appliLoan()
    },
    methods: {
      fechaNormal(arrayData){
        arrayData.forEach(fechaBroder => {
          fechaBroder.date = fechaBroder.date.slice(0,10)
        })
      },
      logout(){
        axios.post("/api/logout")
        .then(() => {
          window.location.href = '/web/index.html'
        })
      },
      todosLosClientes(){
        axios.get("/api/clients/current")
        .then((response) =>{
          this.clientes = response.data;
          this.cuentas = this.clientes.account;
          this.prestamos = this.clientes.loans;
      })
    },
    appliLoan(){
      axios.get("/api/loans")
      .then((response) =>{
        this.loansPropiertis = response.data;
        this.loanCar = this.loansPropiertis.filter(n => n.nameLoan == 'Car')
        this.loanPersonal = this.loansPropiertis.filter(n => n.nameLoan == 'Personal')
        this.loanMort = this.loansPropiertis.filter(n => n.nameLoan == 'Mortgage')
      });
    },
    pedirLoan(){
      accountNumberDeposit = this.accountNumberDeposit.toUpperCase();
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
          axios.post("/api/loans", {nameLoan: this.loanName, amount :this.amount, payments:this.lasCuotas, accountNumberDestiny:this.accountNumberDeposit})
          .then(() => swalWithBootstrapButtons.fire(
            'Awesome!',
            'Your loan was applied.',
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
    computed: {
      aplicandoPorcentajes(){
        if(this.loanName == 'Personal' && this.lasCuotas == 6){
          this.total = ((this.amount * 1.10) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Personal' && this.lasCuotas == 12){
          this.total = ((this.amount * 1.15) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Personal' && this.lasCuotas == 24){
          this.total = ((this.amount * 1.20) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Personal' && this.lasCuotas == 36){
          this.total = ((this.amount * 1.25) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 6){
          this.total = ((this.amount * 1.30) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 12){
          this.total = ((this.amount * 1.35) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 24){
          this.total = ((this.amount * 1.40) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 36){
          this.total = ((this.amount * 1.45) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 12){
          this.total = ((this.amount * 1.5) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 24){
          this.total = ((this.amount * 1.55) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 36){
          this.total = ((this.amount * 1.6) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 48){
          this.total = ((this.amount * 1.65) / (this.lasCuotas))
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 60){
          this.total = ((this.amount * 1.70) / (this.lasCuotas))
          return Math.round(this.total)
        }else{
          return Math.round(this.total) 
        }
      },
      pagarTotal(){
        if(this.loanName == 'Personal' && this.lasCuotas == 6){
          this.total = (this.amount * 1.10)
          return Math.round(this.total)
        }
        if(this.loanName == 'Personal' && this.lasCuotas == 12){
          this.total = (this.amount * 1.15)
          return Math.round(this.total)
        }
        if(this.loanName == 'Personal' && this.lasCuotas == 24){
          this.total = (this.amount * 1.20)
          return Math.round(this.total)
        }
        if(this.loanName == 'Personal' && this.lasCuotas == 36){
          this.total = (this.amount * 1.25)
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 6){
          this.total = (this.amount * 1.30)
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 12){
          this.total = (this.amount * 1.35)
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 24){
          this.total = (this.amount * 1.40)
          return Math.round(this.total)
        }
        if(this.loanName == 'Car' && this.lasCuotas == 36){
          this.total = (this.amount * 1.45)
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 12){
          this.total =(this.amount * 1.5)
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 24){
          this.total = (this.amount * 1.55)
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 36){
          this.total =(this.amount * 1.6)
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 48){
          this.total = (this.amount * 1.65)
          return Math.round(this.total)
        }
        if(this.loanName == 'Mortgage' && this.lasCuotas == 60){
          this.total = (this.amount * 1.70)
          return Math.round(this.total)
        }else{
          return Math.round(this.total)
        }
      },
    },
  }).mount('#app')