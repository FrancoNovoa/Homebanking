const { createApp } = Vue
  createApp({
    data() {
      return {
        clientes: [],
        cuentas: [],
        prestamos: [],
        queryString:'',
        params: '',
        id:'',
        accountType: '',
        accountNumber:'',
        cards:[],
        numberCard:'',
        cardHolder: '',
        amount: '',
        numeroDeCuenta:'',
        cvv: '',
        dateExpire: '',
        description: '',
        accountTo: '',
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
        axios.get("/api/clients/current")
        .then((response) =>{
          this.clientes = response.data;
          this.cuentas = this.clientes.account.filter(active => active.activeAcc).sort((a,b) => a.number - b.number);
          this.prestamos = this.clientes.loans.sort((a,b) => a.id - b.id);
          this.cards = this.clientes.cards.filter(a => a.activeCard);
          console.log(this.cards);
          this.fechaNormal(this.cuentas)
        })
    },
    fechaNormal(arrayData){
      arrayData.forEach(fechaBroder => {
        fechaBroder.creationDate = fechaBroder.creationDate.slice(0,16)
      })
    },
    logout(){
      axios.post("/api/logout")
      .then(() => {
        window.location.href = '/web/index.html'
      })
    },
    createAccount(){
      axios.post("/api/clients/current/accounts", `accountType=${this.accountType}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then(() => 
        Swal.fire({
          position: 'center-center',
          icon: 'success',
          title: 'Your account has been created',
          showConfirmButton: false,
          timer: 1500
        }))
      .then(() => {
        window.location.reload()
      })
    },
    deleteAccount(){
      axios.patch("/api/clients/current/accounts",`numberAcc=${this.accountNumber}`)
      .then(() => window.location.reload())
    },
    transferCard(){
      let accountTo = this.accountTo.toUpperCase();
      axios.post("/api/transactions/payment",{cardNumber:this.numberCard, description:this.description, amount:this.amount, cardHolder:this.cardHolder, accountNumber:this.numeroDeCuenta, cardCvv:this.cvv, thruDate:this.dateExpire, accountNumberTo:accountTo})
      .then(() => 
      Swal.fire({
        position: 'center-center',
        icon: 'success',
        title: 'Your transfer was realized',
        showConfirmButton: false,
        timer: 1500
      }))
      .then(() => window.location.href = '/web/accounts.html?id=' + this.clientes.id)
      .catch(err => Swal.fire({
        icon: 'error',
        title: 'Ops...',
        text: `${err.response.data}`}))
    }
},
    computed: {},
  }).mount('#app')