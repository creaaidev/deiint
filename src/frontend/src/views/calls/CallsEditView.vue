<template>
  <v-card flat>
    <h2>Editar Concurso</h2>
    <v-row>
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="call.name"
          label="Nome do Concurso"
          required
        ></v-text-field>
      </v-col>
      <!-- Leave a bit of space between both -->
      <v-col cols="12" sm="6" md="4">
        <v-checkbox
          v-model="call.interviewsPeriodOpen"
          label="Período de entrevistas aberto"
          :disabled="!initialInterviewsPeriodOpen"
        ></v-checkbox>
        <span v-if="initialInterviewsPeriodOpen && !call.interviewsPeriodOpen">
          <v-icon style="margin: 5px;" color="red">fas fa-exclamation-circle</v-icon>
          <span class="red--text">Esta é uma operação irreversível.</span>
        </span>
      </v-col>
      <!-- Button to send request to backend to createCall -->
      <v-col cols="12" sm="6" md="4">
        <v-btn class="btn" @click="updateCall">Submeter Alterações</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import type CallDto from '@/models/calls/CallDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const id = Number(route.params.id);

let call: CallDto = reactive<CallDto>({
  name: 'Pending...',
  interviewsPeriodOpen: false,
});
let initialInterviewsPeriodOpen = false;

RemoteServices.getCall(id).then((data) => {
  call.id = data.id;
  call.name = data.name;
  call.interviewsPeriodOpen = data.interviewsPeriodOpen;
  if (data.interviewsPeriodOpen)
    initialInterviewsPeriodOpen = data.interviewsPeriodOpen;
});

const updateCall = async () => {
  // TODO: maybe wait until ID is set
  await RemoteServices.updateCall(call);
  alert('Concurso alterado com sucesso!');
};

</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}
</style>
