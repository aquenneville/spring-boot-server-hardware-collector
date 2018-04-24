<template>
  <div class="service">
    <h1>{{ msg }}</h1>
    <h2>REST service call results</h2>

    <button v-on:click="callRestService()">CALL Spring Boot REST backend service</button>
    <button v-on:click="initResponse()">InitResponse</button>
    <h4>Backend response: {{ response }}
    </h4>
    <table class="container">
       <thead>
       <tr>
         <th v-if="display">HostName</th>
         <th v-if="display">Os Name</th>
         <th v-if="display">IP</th>
         <th v-if="display">MacAddress</th>
         <th v-if="display">Cpu</th>
         <th v-if="display">Memory</th>
         <th v-if="display">Motherboard</th>
         <th v-if="display">Disks</th>
       </tr>
       </thead>
      <tbody>
        <tr v-for="config in response">
          <td> {{config.hostname}}</td>
          <td> {{config.osName}}</td>
          <td> {{config.ip}}</td>
          <td> {{config.macAddress}}</td>
          <td> {{config.cpu}}</td>
          <td> {{config.memory}}</td>
          <td> {{config.motherboard}}</td>
          <td v-for="disk in config.disks"> master:{{disk.master}}<br/>model:{{disk.model}}<br>size:{{disk.size}}<br>usage:{{disk.usage}}<br> </td>
        </tr>
      </tbody>
    </table>

    <!-- ul>
      <li v-for="(data, index) in response" :key='index'>{{ index }}. {{ response[index].hostname }} {{ response[index].cpu }} </li><br>
    </ul>
    <b-table :items="response.data" :fields="fields"></b-table -->
  </div>
</template>

<script>
// import axios from 'axios'
import {AXIOS} from './http-common'
export default {
  name: 'service',
  data () {
    return {
      msg: 'HowTo call REST-Services:',
      response: [],
      errors: [],
      display: true,
      fields: ['hostname', 'cpu', 'memory', 'motherboard', 'ip'],
      items: []
    }
  },
  created () {
    this.callRestService()
  },
  watch: {
    '$route': 'callRestService'
  },
  methods: {
    // Fetches posts when the component is created.
    callRestService () {
      AXIOS.get(`api/get-config`)
        .then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data
          this.display = true
          this.items = this.response
          console.log(response.data)
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    initResponse () {
      console.log('initResponse')
      this.response = ''
      this.display = false
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "https://cdn.jsdelivr.net/npm/animate.css@3.5.1";
@import "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css";
  .holder {
    background: #fff;
  }
  ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
  }
  table {
    margin: 0;
    padding: 0;
    list-style-type: none;

  }
  tr {
    padding: 20px;
    font-size: 1.3em;
    background-color: #E0EDF4;
    border-left: 5px solid #3EB3F6;
    margin-bottom: 2px;
    color: #3E5252;
  }
  ul li {
    padding: 20px;
    font-size: 1.3em;
    background-color: #E0EDF4;
    border-left: 5px solid #3EB3F6;
    margin-bottom: 2px;
    color: #3E5252;
  }
  p {
    text-align:center;
    padding: 30px 0;
    color: gray;
  }
  .container {
    box-shadow: 0px 0px 40px lightgray;
  }
  input {
    width: calc(100% - 40px);
    border: 0;
    padding: 20px;
    font-size: 1.3em;
    background-color: #323333;
    color: #687F7F;
  }
  .alert {
    background: #fdf2ce;
    font-weight: bold;
    display: inline-block;
    padding: 5px;
    margin-top: -20px;
  }
  .alert-in-enter-active {
    animation: bounce-in .5s;
  }
  .alert-in-leave-active {
    animation: bounce-in .5s reverse;
  }
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}
i {
  float:right;
  cursor:pointer;
}
</style>
