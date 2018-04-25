<template>
  <div class="service">
    <h1>{{ msg }}</h1>
    <h2>REST service call results</h2>

    <button v-on:click="callRestService()">CALL Spring Boot REST backend service</button>
    <button v-on:click="initResponse()">InitResponse</button>
    <h4>Backend response:<br> {{ resp_pretty }}
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
        <tr v-for="(config, index) in response" :key='index'>
          <td> {{config.hostname}}</td>
          <td> {{config.osName}}</td>
          <td> {{config.ip}}</td>
          <td> {{config.macAddress}}</td>
          <td> {{config.cpu}}</td>
          <td> {{config.memory}}</td>
          <td> {{config.motherboard}}</td>
          <td v-for="(disk, index) in config.disks" :key='index'> master:{{disk.master}}<br/>model:{{disk.model}}<br>size:{{disk.size}}<br>usage:{{disk.usage}}<br></td>
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
          this.resp_pretty = JSON.stringify(response.data, undefined, 2)
          console.log(this.response.data)
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
  h4 {
    padding: 0 25% 0 25%;
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
* {
  box-sizing: border-box;
}
body {
  margin: 0;
}
body{
  background-color:#000000;
}
#izcmut{
  text-align:center;
  border:solid #ffffff;
  background-color:#000000;
  color:#ffffff;
  font-family:Courier New, Courier, monospace;
}
#izpf9t{
  text-align:justify;
  margin:0 0 0 600px;
  font-family:Courier New, Courier, monospace;
}
#i81yil{
  margin:0 0 0 0;
  font-family:Courier New, Courier, monospace;
}
#iyq1kw{
  text-align:center;
  font-family:Courier New, Courier, monospace;
  color:#ffffff;
}
#i0sg6i{
  font-size:14px;
  text-align:center;
}
#iif31i{
  border:1px solid rgb(221, 221, 221);
}
#idk70z{
  border:1px solid rgb(255, 255, 255);
}
#inei2r{
  border:1px solid rgb(255, 255, 255);
}
#ioyjju{
  border:1px solid rgb(255, 255, 255);
}
#ipm7m6{
  border-radius:0 0 0 0;
  border:1px solid rgb(255, 255, 255);
}
#icviby{
  border:1px solid rgb(255, 255, 255);
}
#iqh5bj{
  border:1px solid rgb(255, 255, 255);
}
#izh654{
  border:1px solid rgb(255, 255, 255);
}
#i020ho{
  border:1px solid #ffffff;
}
.row{
  display:table;
  padding-top:10px;
  padding-right:10px;
  padding-bottom:10px;
  padding-left:10px;
  width:100%;
}
.cell{
  width:8%;
  display:table-cell;
  height:75px;
}
.header-banner{
  padding-top:35px;
  padding-bottom:100px;
  font-family:Helvetica, serif;
  font-weight:100;
  background-position-x:0%, 50%;
  background-position-y:0%, 50%;
  background-repeat-x:no-repeat, no-repeat;
  background-repeat-y:repeat, no-repeat;
  max-width:auto;
  color:#000000;
  border:solid #000000;
}
*{
  box-sizing:border-box;
}
body{
  margin-top:0px;
  margin-right:0px;
  margin-bottom:0px;
  margin-left:0px;
}
.service{
  font-family:Courier New, Courier, monospace;
  font-weight:700;
}
.c6694{
  float:none;
  left:50%;
  right:0;
  position:static;
  font-family:Courier New, Courier, monospace;
  text-align:center;
  color:#ffffff;
}
.container{
  font-size:12px;
  padding:0 0 0 0;
  margin:0 0 0 0;
  font-family:Courier New, Courier, monospace;
  text-align:left;
  color:#ffffff;
  border:solid #dddddd;
  position: relative;
  margin-left:auto;
  margin-right:auto;
}
@media (max-width: 768px){
  .cell{
    width:100%;
    display:block;
  }
  .row{
    flex-wrap:wrap;
  }
}
@keyframes fadeEffect{
  0%{
    opacity:0;
  }
  100%{
    opacity:1;
  }
}
</style>
