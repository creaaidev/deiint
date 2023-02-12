<template>
  <v-card flat>
    <h2>Concursos Atuais</h2>
    <v-text-field
      v-model="search"
      append-inner-icon="fas fa-search"
      label="Pesquisar"
      single-line
      hide-details
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="calls"
      :search="search"
      item-key="id"
      no-data-text="Sem concursos a apresentar."
    >
      <template v-slot:item.interviewsPeriodOpen="{ item }">
        <v-checkbox-btn
          v-model="item.raw.interviewsPeriodOpen"
          disabled
        ></v-checkbox-btn>
      </template> <!-- Actions column -->
      <template v-slot:item.actions="{ item }">
        <!-- End application period -->
        <v-btn
          v-bind:active="item.raw.interviewsPeriodOpen"
          color="success"
          class="mr-2"
          :to="{ name: 'calls-edit', params: { id: item.id } }"
        >
          <v-icon>fas fa-check</v-icon>
        </v-btn>
        <!-- Edit button -->
        <v-btn
          color="primary"
          class="mr-2"
          :to="{ name: 'calls-edit', params: { id: item.id } }"
        >
          <v-icon>fas fa-edit</v-icon>
        </v-btn>
        <!-- Delete button -->
        <v-btn color="error" class="mr-2"
          @click="deleteCall(item.id)"
        >
          <v-icon>fas fa-trash</v-icon> <!-- TODO: Remember more stuff needs to happen -->
        </v-btn>
      </template>
    </v-data-table>
  </v-card>
</template>

<script setup lang="ts">
import type CallDto from '@/models/calls/CallDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
const headers = [
  { title: 'ID', value: 'id', sortable: true, filterable: false },
  { title: 'Nome', value: 'name', sortable: true, filterable: true },
  {
    title: 'Período de Entrevistas Aberto',
    value: 'interviewsPeriodOpen',
    key: 'interviewsPeriodOpen',
    sortable: true,
    filterable: false,
    groupable: true,
  },
  { title: 'Ações', value: 'actions', sortable: false, filterable: false},
];

let calls: CallDto[] = reactive([]);

RemoteServices.getCalls().then((data) => {
  calls.push(...data);
});

const deleteCall = (id: number) => {
  RemoteServices.deleteCall(id).then(() => {
    calls = calls.filter((call) => call.id !== id);
  });
};

</script>
