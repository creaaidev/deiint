<template>
  <v-card flat>
    <h2>Editar Entrevista</h2>
    <v-row>
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
        <p v-if="interview.callName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          Selecionado: {{ interview.callName }}
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
        <p v-if="interview.candidateName"
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          Selecionado: {{ interview.candidateName }}, ID: {{ interview.candidateId }}
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
        <p v-if="interview.roomName" 
        class="w-full rounded border border-gray-300 px-4 py-2 searchList searchResult" >
          Selecionado: {{ interview.roomName }}
        </p>
      </v-col>
      <!-- Add overflow!! somehow -->
      <v-col cols="1" sm="1" md="3">
        <v-select
          v-model="interview.status"
          :items="possibleStatuses"
          label="Estado"
          required
        ></v-select>
      </v-col>
      <!-- Button to send request to backend to createCall -->
    </v-row>
    <v-row>
      <v-col style="text-align: center;">
        <v-btn style="margin: 5px" class="btn" @click="updateInterview">Submeter Alterações</v-btn>
      </v-col>
    </v-row>
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

let interview: InterviewDto = reactive<InterviewDto>({
  callName: 'PENDENTE',
  candidateName: 'PENDENTE',
  roomName: 'PENDENTE',
});

// List of possible statuses for a interview
const possibleStatuses = [
  'AGENDADA',
  'TERMINADA',
  'CANCELADA',
  'PENDENTE',
  'REAGENDADA',
];

let calls: CallDto[] = reactive([]);
let rooms: RoomDto[] = reactive([]);
let candidates: CandidateDto[] = reactive([]);
let ratings: RatingDto[] = reactive([]);

// TODO: filter available rooms
RemoteServices.getCalls().then((data) => {
  calls.push(...data);
});
RemoteServices.getRooms().then((data) => {
  rooms.push(...data.filter((room) => room.available)); // TODO: UNAVAILABLE ROOMS WONT SHOW UP
});
RemoteServices.getCandidates().then((data) => {
  candidates.push(...data);
});

let sresultCall = ref('');
let sresultRoom = ref('');
let sresultCandidate = ref('');

RemoteServices.getInterview(id).then((data) => {
  interview.callId = data.callId;
  interview.candidateId = data.candidateId;
  interview.roomId = data.roomId;
  interview.status = data.status;
  interview.id = data.id;

  if (interview.callId) {
    calls.filter((call) => call.id === interview.callId).forEach((call) => {
      interview.callName = call.name;
    });
  }

  if (interview.candidateId) {
    candidates.filter((candidate) => candidate.id === interview.candidateId).forEach((candidate) => {
      interview.candidateName = candidate.name;
    });
  }

  if (interview.roomId) {
    rooms.filter((room) => room.id === interview.roomId).forEach((room) => {
      interview.roomName = room.name;
    });
  }
});

// Get ratings
RemoteServices.getRatingsByInterviewId(id).then((data) => {
  ratings.push(...data);
  
  // Remove TERMINADA from possible status if there are no ratings
  if (ratings.length === 0) {
    possibleStatuses.splice(possibleStatuses.indexOf('TERMINADA'), 1);
  }
});

const updateInterview = async () => {
  // TODO: maybe wait until ID is set
  await RemoteServices.updateInterview(interview);
  alert('Entrevista alterada com sucesso!');
};

// --- Autocompletion --- //

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
  interview.callId = call.id;
  interview.callName = call.name;
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
  interview.roomId= room.id;
  interview.roomName = room.name;
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
  interview.candidateId = candidate.id;
  interview.candidateName = candidate.name;
  sresultCandidate.value = '';
};
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

.searchListItem {
  cursor: pointer;
}

.searchListItem:hover {
  background-color: #3c8dbc;
  color: white;
}
</style>