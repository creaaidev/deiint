<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <v-card flat>
    <h2>Adicionar Candidato</h2>
    <v-row>
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="newCandidate.name"
          label="Nome do Candidato"
          required
        ></v-text-field>
      </v-col>
      <!-- Leave a bit of space between both -->
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="newCandidate.email"
          label="Email"
          required
        ></v-text-field>
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-btn class="btn" @click="createCandidate">Adicionar Candidato</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import type CandidateDto from '@/models/candidates/CandidateDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let newCandidate = reactive<CandidateDto>({
  name: '',
  email: '',
});

// Alert boolean to notify of success creating call

const createCandidate = async () => {
  await RemoteServices.createCandidate(newCandidate);
  // After creating the call, reset the form and notify the user of success
  newCandidate.name = '';
  newCandidate.email = '';
  // Alert = true;
  alert('Candidato adicionado com sucesso!');
};

</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}
</style>
