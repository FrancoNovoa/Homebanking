const { createApp } = Vue
  createApp({
    data() {
      return {
        clientes:[],
        tarjetas:[],
        queryString:'',
        params: '',
        id:'',
        cardType: '',
        cardColor: '',
        tarjetasActivas:[],
        selectCartaBorrar:[],
        diaActual: '',
        dia:'',
        mes: '',
        ano: '',
        tiempoTranscurrido:'',
      }
    },
    created(){
      this.queryString = location.search
      this.params = new URLSearchParams(this.queryString)
      this.id = this.params.get('id')
      this.todasLasCuentas()
      this.tiempoTranscurrido = Date.now();
      this.diaActual = new Date(this.tiempoTranscurrido).toISOString().slice(2,7);
      console.log(this.diaActual)
    },
    methods: {
      todasLasCuentas(){
        axios.get("/api/clients/current")
        .then(respuesta => {
          this.clientes = respuesta.data;
          this.tarjetas = this.clientes.cards;
          this.tarjetasActivas = this.tarjetas.filter(active => active.activeCard)
          this.fechaNormal(this.tarjetas)
        })
      },
      fechaNormal(arrayData){
        arrayData.forEach(fechaBroder => {
          fechaBroder.untilDate = fechaBroder.untilDate.slice(2,7)
        })
      },
      logout(){
        axios.post("/api/logout")
        .then(() => {
          window.location.href = '/web/index.html'
        })
      },
      createCards(){
        axios.post("/api/clients/current/cards",`cardColor=${this.cardColor}&cardType=${this.cardType}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then(() => 
        Swal.fire({
          position: 'center-center',
          icon: 'success',
          title: 'Your card has been created',
          showConfirmButton: false,
          timer: 1500
        })
      )
      .then(() =>{
        window.location.href = '/web/cards.html'})
        .catch(err => Swal.fire({
          icon: 'error',
          title: 'Ops...',
          text: `${err.response.data}`}))
      },
      deleteCard(){
        axios.patch("/api/clients/current/cards", `cardNumber=${this.selectCartaBorrar}`)
        .then(() => 
        Swal.fire({
          position: 'center-center',
          icon: 'success',
          title: 'Your card has been deleted',
          showConfirmButton: false,
          timer: 1500
        })
      )
      .then(() =>{
        window.location.href = '/web/cards.html'})
        .catch(err => Swal.fire({
          icon: 'error',
          title: 'Ops...',
          text: `${err.response.data}`}))
      }
    },
    computed: {},
  }).mount('#app')