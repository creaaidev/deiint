<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <v-card flat>
    <h2>Editar Candidato</h2>
    <v-row>
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="candidate.name"
          label="Nome do Candidato"
          required
        ></v-text-field>
      </v-col>
      <!-- Leave a bit of space between both -->
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="candidate.email"
          label="Email"
          required
        ></v-text-field>
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-btn class="btn" @click="updateCandidate">Editar Candidato</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import type CandidateDto from '@/models/candidates/CandidateDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const id = Number(route.params.id);

let candidate = reactive<CandidateDto>({
  name: 'Pending...',
  email: 'Pending...',
});

// Alert boolean to notify of success creating call

RemoteServices.getCandidate(id).then((data) => {
  candidate.id = data.id;
  candidate.name = data.name;
  candidate.email = data.email;
});

const updateCandidate = async () => {
  // TODO: maybe wait until ID is set
  await RemoteServices.updateCandidate(candidate);
  // Alert = true;
  alert('Candidato editado com sucesso!');
};

</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}
</style>
