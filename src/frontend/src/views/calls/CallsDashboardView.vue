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
  // TODO: maybe add another column with possible actions? (edit / delete)
];

let calls: CallDto[] = reactive([]);

RemoteServices.getCalls().then((data) => {
  calls.push(...data);
});
</script>
