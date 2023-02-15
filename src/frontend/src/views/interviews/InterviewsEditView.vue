<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <!-- Card to create a new call -->
  <v-card flat>
    <h2>Editar Entrevista</h2>
    <v-row>
      <v-col cols="12" sm="6" md="4">
        <v-select
          v-model="interview.callName"
          :items="calls"
          label="Concurso"
          required
        >
          <v-slot v-slot:item="{ item }">
            <template v-if="item">
              {{ item.name }}
            </template>
          </v-slot>
        </v-select>
      </v-col>
      <!-- Leave a bit of space between both -->
      <v-col cols="12" sm="6" md="4">
        <v-select
          v-model="interview.roomName"
          :items="rooms"
          label="Sala"
          required
        ></v-select>
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-select
          v-model="interview.candidateName"
          :items="candidates"
          label="Candidato"
          required
        ></v-select>
      </v-col>
      <v-col cols="12" sm="6" md="4">
        <v-text-field
          v-model="interview.status"
          label="Estado"
        ></v-text-field>
      </v-col>
      <!-- Button to send request to backend to createCall -->
      <v-col cols="12" sm="6" md="4">
        <v-btn class="btn" @click="updateInterview">Submeter Alterações</v-btn>
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
import { useRoute } from 'vue-router';

const route = useRoute();
const id = Number(route.params.id);

// Get Calls, Rooms and Candidates to populate dropdown menus
let calls: CallDto[] = reactive([]);
let rooms: RoomDto[] = reactive([]);
let candidates: CandidateDto[] = reactive([]);

let interview: InterviewDto = reactive<InterviewDto>({
  callName: 'Pending...',
  roomName: 'Pending...',
  candidateName: 'Pending...',
  status: 'Pending...',
});

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

RemoteServices.getInterview(id).then((data) => {
  interview.id = data.id;
  interview.callName = data.callName;
  interview.roomName = data.roomName;
  interview.candidateName = data.candidateName;
  interview.status = data.status;
});

// Alert boolean to notify of success creating call

const updateInterview = async () => {
  // TODO: maybe wait until ID is set
  await RemoteServices.updateInterview(interview);
  // After updating the call, reset the form and notify the user of success
  // Alert = true;
  alert('Entrevista alterada com sucesso!');
};

</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}
</style>
