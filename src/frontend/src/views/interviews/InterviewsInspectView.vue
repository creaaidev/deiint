<template>
  <!-- Alert to notify of success creating call,
     should only appear after createCall has been called
     and should disappear after 10 seconds. Furthermore
    it should display over the v-card -->
  <!-- Card to create a new call -->
  <v-card v-if="submissionProcess" flat>
    <p>teste!</p>
  </v-card>
  <v-card flat>
    <h2>Entrevista</h2>
    <v-row>
      <v-col cols="12" sm="6" md="3">
        <label>Concurso</label>
        <p v-if="interview.callName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          {{ interview.callName }}
        </p>
      </v-col>
      <!-- Candidate Input Field with Autocompletion -->
      <v-col cols="12" sm="6" md="3">
        <label>Candidato</label>
        <p v-if="interview.candidateName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          {{ interview.candidateName }}, ID: {{ interview.candidateId }}
        </p>
      </v-col>
      <v-col cols="12" sm="6" md="3">
        <label>Sala</label>
        <p v-if="interview.roomName" 
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          {{ interview.roomName }}
        </p>
      </v-col>
      <!-- Add overflow!! somehow -->
      <v-col cols="1" sm="1" md="3">
        <label>Estado</label>
        <p v-if="interview.status" 
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          {{ interview.status }}
        </p>
      </v-col>
      <!-- Button to send request to backend to createCall -->
    </v-row>
    <v-row>
      <v-col>
        <v-btn
          class="btn"
          color="primary"
          :disabled="interview.status === 'TERMINADA' || interview.status === 'CANCELADA'"
          @click="submissionProcess = true"
        > <!-- TODO: Change to another name? -->
          Submeter Avaliação
        </v-btn>
      </v-col>
    </v-row>
    <!-- This should probably be a ENUM or something -->
    <div style="margin-top: 15px;"
     v-if="interview.status != 'TERMINADA' && interview.status != 'CANCELADA'">
      <h2>Avaliações</h2>
      <v-data-table
        :headers="headers"
        :items="ratings"
        :search="search"
        multi-sort
      >

      </v-data-table>
    </div>
  </v-card>
</template>

<script setup lang="ts">
import type InterviewDto from '@/models/interviews/InterviewDto';
import type CallDto from '@/models/calls/CallDto';
import type RoomDto from '@/models/rooms/RoomDto';
import type CandidateDto from '@/models/candidates/CandidateDto';
import type RatingDto from '@/models/ratings/RatingDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref, computed} from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const id = Number(route.params.id);

let submissionProcess = ref(false);

let interview: InterviewDto = reactive<InterviewDto>({
  callName: 'PENDENTE',
  candidateName: 'PENDENTE',
  roomName: 'PENDENTE',
});

// List of possible statuses for a interview
const possibleStatuses = [
  'AGENDADA',
  'REALIZADA',
  'CANCELADA',
  'PENDENTE',
  'REAGENDADA',
];

let search = ref('');
const headers = [
    { title: 'Examinador', value: 'examinerName', key: 'examinerName', sortable: false, filterable: true },
    { title: 'Software', value: 'ss', key: 'ss', sortable: true, filterable: true },
    { title: 'Hardware', value: 'hw', key: 'hw', sortable: true, filterable: true },
    { title: 'Rac.Prog.', value: 'rp', key: 'rp', sortable: true, filterable: true },
    { title: 'Linux', value: 'lin', key: 'lin', sortable: true, filterable: true },
    { title: 'Windows', value: 'win', key: 'win', sortable: true, filterable: true },
];

let call: CallDto = reactive({});
let room: RoomDto = reactive({});
let candidate: CandidateDto = reactive({});
let ratings: RatingDto[] = reactive([]);

RemoteServices.getInterview(id).then((data) => {
  interview.callId = data.callId;
  interview.candidateId = data.candidateId;
  interview.roomId = data.roomId;
  interview.status = data.status;
  interview.id = data.id;

  if (interview.callId) {
    RemoteServices.getCall(interview.callId).then((data) => {
      call = data;
      interview.callName = data.name;
    });
  }

  if (interview.candidateId) {
    RemoteServices.getCandidate(interview.candidateId).then((data) => {
      candidate = data;
      interview.candidateName = data.name;
    });
  }

  if (interview.roomId) {
    RemoteServices.getRoom(interview.roomId).then((data) => {
      room = data;
      interview.roomName = data.name;
    });
  }
});

// Get ratings
RemoteServices.getRatingsByInterviewId(id).then((data) => {
  ratings.push(...data);
});

// Alert boolean to notify of success creating call

</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}

.searchList {
  list-style-type: none;
}

.searchResult {
  background-color: rgb(207, 207, 207);
  cursor: default;
}

</style>