<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <!-- Card to create a new call -->
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
        ></v-checkbox>
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
  name: '',
  interviewsPeriodOpen: false,
});

RemoteServices.getCall(id).then((data) => {
  call.id = data.id;
  call.name = data.name;
  call.interviewsPeriodOpen = data.interviewsPeriodOpen;
});

// Alert boolean to notify of success creating call

const updateCall = async () => {
  // TODO: maybe wait until ID is set
  await RemoteServices.updateCall(call);
  // After updating the call, reset the form and notify the user of success
  // Alert = true;
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
