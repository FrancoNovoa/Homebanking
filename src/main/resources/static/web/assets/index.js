const { createApp } = Vue
  createApp({
    data() {
      return {
        email: "",
        password: "",
        nameRegister: "",
        emailRegister: "",
        passwordRegister: "",
        lastNameRegister: "",
      }
    },
    created(){
    },
    methods: {
      login(){
        axios.post("/api/login",`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
        .then(()=>{
          window.location.href = '/web/landing.html'
        })
        .catch(err => Swal.fire({
          icon: 'error',
          title: 'Ops...',
          text: 'The credentials are incorrect'}))
      },
      register(){
        axios.post("/api/clients",`name=${this.nameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.passwordRegister}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
        .then(response =>axios.post("/api/login", `email=${this.emailRegister}&password=${this.passwordRegister}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}))
        .then(response =>{axios.post("/api/clients/current/accounts",`clientEmail=${this.emailRegister}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
        window.location.href = '/web/landing.html'})
        .catch(err => Swal.fire({
          icon: 'error',
          title: 'Ops...',
          text: `${err.response.data}`}))
      }
    },
    computed: {},
  }).mount('#app')
const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");
sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
});
sign_in_btn.addEventListener("click", () => {
container.classList.remove("sign-up-mode");
}); 