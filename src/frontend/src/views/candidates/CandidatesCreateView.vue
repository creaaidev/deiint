<template>
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

const createCandidate = async () => {
  await RemoteServices.createCandidate(newCandidate);
  newCandidate.name = '';
  newCandidate.email = '';
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
