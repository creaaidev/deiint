<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <!-- Card to create a new call -->
  <v-card flat>
    <h2>Criar Concurso</h2>
    <v-row>
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="newCall.name"
          label="Nome do Concurso"
          required
        ></v-text-field>
      </v-col>
      <!-- Leave a bit of space between both -->
      <v-col cols="12" sm="6" md="4">
        <v-checkbox
          v-model="newCall.interviewsPeriodOpen"
          label="Período de entrevistas aberto"
        ></v-checkbox>
      </v-col>
      <!-- Button to send request to backend to createCall -->
      <v-col cols="12" sm="6" md="4">
        <v-btn class="btn" @click="createCall">Criar Concurso</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import type CallDto from '@/models/calls/CallDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let newCall = reactive<CallDto>({
  name: '',
  interviewsPeriodOpen: false,
});

// Alert boolean to notify of success creating call

const createCall = async () => {
  await RemoteServices.createCall(newCall);
  // After creating the call, reset the form and notify the user of success
  newCall.name = '';
  newCall.interviewsPeriodOpen = false;
  // Alert = true;
  alert('Concurso criado com sucesso!');
};
</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}
</style>
