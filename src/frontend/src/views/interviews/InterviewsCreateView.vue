<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <!-- Card to create a new call -->
  <v-card flat>
    <h2>Criar Entrevista</h2>
    <v-row>
      <v-col cols="12" sm="6" md="4">
        <v-select
          v-model="call"
          :items="calls"
          item-text="name"
          label="Concurso"
          required
        ></v-select>
      </v-col>
      <!-- Leave a bit of space between both -->
      <v-col cols="12" sm="6" md="4">
        <v-select
          v-model="newInterview.roomName"
          :items="rooms"
          label="Sala"
          required
        ></v-select>
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-select
          v-model="newInterview.candidateName"
          :items="candidates"
          label="Candidato"
          required
        ></v-select>
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="newInterview.status"
          label="Estado"
        ></v-text-field>
      </v-col>
      <!-- Button to send request to backend to createCall -->
      <v-col cols="12" sm="6" md="4">
        <v-btn class="btn" @click="createInterview">Criar Entrevista</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import type InterviewDto from '@/models/interviews/InterviewDto';
import type CallDto from '@/models/calls/CallDto';
import type RoomDto from '@/models/rooms/RoomDto';
import type CandidateDto from '@/models/candidates/CandidateDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

// Get Calls, Rooms and Candidates to populate dropdown menus
let calls: CallDto[] = reactive([]);
let rooms: RoomDto[] = reactive([]);
let candidates: CandidateDto[] = reactive([]);

// :) ideally these variables wouldn't be necessary but 
// i dont know enough vue to make better dropdowns
// maybe after im accepted ??? pog?
let call: CallDto = reactive<CallDto>({});
let room: RoomDto = reactive<RoomDto>({});
let candidate: CandidateDto = reactive<CandidateDto>({});

let newInterview = reactive<InterviewDto>({});

// TODO: filter available rooms
RemoteServices.getCalls().then((data) => {
  calls.push(...data);
});
RemoteServices.getRooms().then((data) => {
  rooms.push(...data);
});
RemoteServices.getCandidates().then((data) => {
  candidates.push(...data);
});

// Alert boolean to notify of success creating call

const createInterview = async () => {
  await RemoteServices.createInterview(newInterview);
  // After creating the call, reset the form and notify the user of success
  newInterview.callName = 'SELECIONAR';
  newInterview.roomName = 'SELECIONAR';
  newInterview.candidateName = 'SELECIONAR';
  newInterview.status = '';
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
