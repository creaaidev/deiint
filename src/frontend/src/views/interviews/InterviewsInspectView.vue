<template>
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
    </v-row>
    <v-row>
      <v-col style="text-align: center;">
        <v-btn
          class="btn"
          color="primary"
          v-if="interview.status != 'TERMINADA' && interview.status != 'CANCELADA'"
          @click="submissionProcess = true"
          style="margin: 5px;"
        >
          Nova Avaliação
        </v-btn>
        <v-btn
          class="btn"
          color="primary"
          v-if="interview.status === 'TERMINADA'"
          @click="submitResults"
          style="margin: 5px;"
        >
          Enviar Resultados
        </v-btn>
      </v-col>
    </v-row>
    <v-card v-if="submissionProcess" flat
      class="w-full rounded border border-gray-300 px-4 py-2 ratingSubmissionForm"
      style="margin: 20px;"
    >
      <h2>Nova Avaliação</h2>
      <v-row>
        <v-col cols="1" sm="1" md="3">
          <v-text-field
            type="text"
            id="examinerName"
            v-model="newRating.examinerName"
            label="Examinador"
            placeholder="..."
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" sm="1" md="1">
          <v-text-field
            type="number"
            id="softSkills"
            v-model.number="newRating.ss"
            min="0"
            max="5"
            label="SS"
            placeholder="..."
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" sm="1" md="1">
          <v-text-field
            type="number"
            id="hardware"
            v-model.number="newRating.hw"
            min="0"
            max="5"
            label="HW"
            placeholder="..."
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" sm="1" md="1">
          <v-text-field
            type="number"
            id="raciocinioProg"
            v-model.number="newRating.rp"
            min="0"
            max="5"
            label="RP"
            placeholder="..."
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" sm="1" md="1">
          <v-text-field
            type="number"
            id="linux"
            v-model.number="newRating.lin"
            min="0"
            max="5"
            label="LIN"
            placeholder="..."
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" sm="1" md="1">
          <v-text-field
            type="number"
            id="windows"
            v-model.number="newRating.win"
            min="0"
            max="5"
            label="WIN"
            placeholder="..."
            required
          ></v-text-field>
        </v-col>
        <v-col style="text-align: center;">
          <v-btn
            class="btn"
            color="primary"
            style="margin-top: 10px;"
            @click="submitRating"
          >
            Submeter Avaliação
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
    <!-- This should probably be a ENUM or something -->
    <div style="margin-top: 15px;"
     v-if="interview.status != 'CANCELADA'">
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
import type RatingDto from '@/models/ratings/RatingDto';
import type ResultDto from '@/models/results/ResultDto';
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
    { title: 'Soft Skills', value: 'ss', key: 'ss', sortable: true, filterable: true },
    { title: 'Hardware', value: 'hw', key: 'hw', sortable: true, filterable: true },
    { title: 'Rac.Prog.', value: 'rp', key: 'rp', sortable: true, filterable: true },
    { title: 'Linux', value: 'lin', key: 'lin', sortable: true, filterable: true },
    { title: 'Windows', value: 'win', key: 'win', sortable: true, filterable: true },
];

let call: CallDto = reactive({});
let ratings: RatingDto[] = reactive([]);

let newRating: RatingDto = reactive({});

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
      interview.candidateName = data.name;
    });
  }

  if (interview.roomId) {
    RemoteServices.getRoom(interview.roomId).then((data) => {
      interview.roomName = data.name;
    });
  }
});

// Get ratings
RemoteServices.getRatingsByInterviewId(id).then((data) => {
  ratings.push(...data);
});

// Create rating
const submitRating = async () => {
  newRating.interviewId = interview.id;
  await RemoteServices.createRating(newRating);
  submissionProcess.value = false;
  console.log('Avaliação submetida com sucesso!');
  window.location.reload();
};

let results: ResultDto = reactive({
  ratings: {
    ss: 0,
    rp: 0,
    hw: 0,
    lin: 0,
    win: 0,
  }
});

const submitResults = async () => {
  results.call = call.name;

  // This is so bad holy ****, i sure hope there's a better way to do this
  // I saw somewhere about the <Suspense> or whatever component but
  // no time to learn it now :sob:, Undefined attributes are my enemies fr fr
  ratings.forEach((rating) => {
    if (rating.ss && results.ratings && results.ratings.ss)
      results.ratings.ss += (rating.ss / ratings.length);
    if (rating.rp && results.ratings && results.ratings.rp)
      results.ratings.rp += (rating.rp / ratings.length);
    if (rating.hw && results.ratings && results.ratings.hw)
      results.ratings.hw += (rating.hw / ratings.length);
    if (rating.lin && results.ratings && results.ratings.lin)
      results.ratings.lin += (rating.lin / ratings.length);
    if (rating.win && results.ratings && results.ratings.win)
      results.ratings.win += (rating.win / ratings.length);
  }); 
  // Amen

  if (interview.candidateId)
    await RemoteServices.submitResults(results, interview.candidateId);
  // This doesnt work s o   s   a   d
  // Ratings continue to be 0 for whatever reason
}

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

.ratingSubmissionForm {
  background-color: rgb(243, 243, 243);
}

</style>