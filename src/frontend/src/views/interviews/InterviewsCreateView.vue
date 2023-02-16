<template>
  <v-card flat>
    <h2>Criar Entrevista</h2>
    <v-row>
      <!-- Call Input Field with Autocompletion -->
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          type="text"
          id="search"
          v-model="sresultCall"
          label="Nome do Concurso"
          placeholder="..."
          required
        ></v-text-field>
        <ul v-if="searchCalls.length"
        class="w-full rounded bg-white border border-gray-300 px-4 py-2 searchList" >
          <li class="pb-2 border-b border-gray-200" >
            Showing {{ searchCalls.length }} of {{ calls.length }} results
          </li>
          <li
            v-for="call in searchCalls"
            :key="call.name"
            @click="selectCall(call)"
            class="p-1 searchListItem"
          >
            {{ call.name }}
          </li>
        </ul>
        <p v-if="newInterview.callName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          Selecionado: {{ newInterview.callName }}
        </p>
      </v-col>
      <!-- Candidate Input Field with Autocompletion -->
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          type="text"
          id="search"
          v-model="sresultCandidate"
          label="Nome do Candidato"
          placeholder="..."
          required
        ></v-text-field>
        <ul v-if="searchCandidates.length"
        class="w-full rounded bg-white border border-gray-300 px-4 py-2 searchList" >
          <li class="pb-2 border-b border-gray-200" >
            Showing {{ searchCandidates.length }} of {{ candidates.length }} results
          </li>
          <li
            v-for="candidate in searchCandidates"
            :key="candidate.name"
            @click="selectCandidate(candidate)"
            class="p-1 searchListItem"
          >
            {{ candidate.name }}, ID: {{ candidate.id }}
          </li>
        </ul>
        <p v-if="newInterview.candidateName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          Selecionado: {{ newInterview.candidateName }}, ID: {{ newInterview.candidateId }}
        </p>
      </v-col>
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          type="text"
          id="search"
          v-model="sresultRoom"
          label="Sala"
          placeholder="..."
          required
        ></v-text-field>
        <ul v-if="searchRooms.length"
        class="w-full rounded bg-white border border-gray-300 px-4 py-2 searchList" >
          <li class="pb-2 border-b border-gray-200" >
            Showing {{ searchRooms.length }} of {{ rooms.length }} results
          </li>
          <li
            v-for="room in searchRooms"
            :key="room.name"
            @click="selectRoom(room)"
            class="p-1 searchListItem"
          >
            {{ room.name }}
          </li>
        </ul>
        <p v-if="newInterview.roomName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          Selecionado: {{ newInterview.roomName }}
        </p>
      </v-col>
      <!-- Add overflow!! somehow -->
      <v-col cols="1" sm="1" md="2">
        <v-select
          v-model="newInterview.status"
          :items="possibleStatuses"
          label="Estado"
          required
        ></v-select>
      </v-col>
    </v-row>
    <v-row>
      <!-- Button to send request to backend to createCall -->
      <v-col style="text-align: center;">
        <v-btn style="margin: 5px;" class="btn" @click="createInterview">Criar Entrevista</v-btn>
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
import { reactive, ref, computed} from 'vue';

let newInterview = reactive<InterviewDto>({});

// List of possible statuses for a call
const possibleStatuses = [
  'AGENDADA',
  'CANCELADA',
  'PENDENTE',
  'REAGENDADA',
];

// Alert boolean to notify of success creating call

const createInterview = async () => {
  await RemoteServices.createInterview(newInterview);
  // After creating the call, reset the form and notify the user of success
  newInterview.callName = '';
  newInterview.roomName = '';
  newInterview.candidateName = '';
  newInterview.status = '';
  // Alert = true;
  alert('Concurso criado com sucesso!');
};

// --- Autocompletion System --- //

// Get Calls, Rooms and Candidates to populate dropdown menus
let calls: CallDto[] = reactive([]);
let rooms: RoomDto[] = reactive([]);
let candidates: CandidateDto[] = reactive([]);

let sresultCall = ref('');
let sresultRoom = ref('');
let sresultCandidate = ref('');

// TODO: filter available rooms
RemoteServices.getCalls().then((data) => {
  calls.push(...data.filter((call) => call.interviewsPeriodOpen));
});
RemoteServices.getRooms().then((data) => {
  rooms.push(...data.filter((room) => room.available));
});
RemoteServices.getCandidates().then((data) => {
  candidates.push(...data);
});

const searchCalls = computed(() => {
  if (sresultCall.value === '') {
    return []
  }

  let matches = 0
  // Compare the search string to the call name (case insensitive),  calls could be undefined
  return calls.filter(call => {
    if (call.name) { // bad fix but idc
      if (
        call.name.toLowerCase().includes(sresultCall.value.toLowerCase())
        && matches < 10
      ) {
        matches++
        return call
      }
    }
  })
});

// selectCall function that sets the callId attribute of newInterview
const selectCall = (call: CallDto) => {
  newInterview.callId = call.id;
  newInterview.callName = call.name;
  sresultCall.value = '';
};

const searchRooms = computed(() => {
  if (sresultRoom.value === '') {
    return []
  }

  let matches = 0
  // Compare the search string to the call name (case insensitive),  calls could be undefined
  return rooms.filter(room => {
    if (room.name) { // bad fix but idc
      if (
        room.name.toLowerCase().includes(sresultRoom.value.toLowerCase())
        && matches < 10
      ) {
        matches++
        return room
      }
    }
  })
});

const selectRoom = (room: RoomDto) => {
  newInterview.roomId= room.id;
  newInterview.roomName = room.name;
  sresultRoom.value = '';
};


const searchCandidates = computed(() => {
  if (sresultCandidate.value === '') {
    return []
  }

  let matches = 0
  // Compare the search string to the call name (case insensitive),  calls could be undefined
  return candidates.filter(candidate => {
    if (candidate.name) { // bad fix but idc
      if (
        candidate.name.toLowerCase().includes(sresultCandidate.value.toLowerCase())
        && matches < 10
      ) {
        matches++
        return candidate
      }
    }
  })
});

const selectCandidate = (candidate: CandidateDto) => {
  newInterview.candidateId = candidate.id;
  newInterview.candidateName = candidate.name;
  sresultCandidate.value = '';
};


</script>

<!-- CSS Style for buttons and whatnot -->
<style> 
.btn:hover {
  background-color: #3c8dbc;
  color: white;
}

.searchResult {
  background-color: rgb(207, 207, 207);
  cursor: default;
}

.searchList {
  list-style-type: none;
}

.searchListItem {
  cursor: pointer;
}

.searchListItem:hover {
  background-color: #3c8dbc;
  color: white;
}

</style>
