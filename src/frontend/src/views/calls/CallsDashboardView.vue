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
      multi-sort
      locale="pt-PT"
      loading="A carregar concursos.."
      no-data-text="Sem concursos a apresentar."
      no-results-text="Nenhum concurso corresponde aos critérios indicados"
    >
      <template v-slot:item="{ item }">
        <!-- I'm sorry for the quality of this snippet, I just couldn't 
              get it working any other way and I've already spent way too
              much time on this, really -->
        <tr>
          <td style="padding-left: 1%" >{{ item.raw.id }}</td>
          <td>{{ item.raw.name }}</td>
          <td>
            <v-checkbox-btn
            v-model="item.raw.interviewsPeriodOpen"
            disabled
            ></v-checkbox-btn>
          </td>
          <td>
            <v-btn
              color="primary"
            >
              <v-icon>fas fa-cog</v-icon>
            </v-btn>
            <v-btn
              color="error"
            >
              <v-icon>fas fa-trash</v-icon>
            </v-btn>
          </td>
        </tr>
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
  { title: 'ID', value: 'id', key: 'id', sortable: true, filterable: false },
  { title: 'Nome', value: 'name', key: 'name', sortable: true, filterable: true },
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
