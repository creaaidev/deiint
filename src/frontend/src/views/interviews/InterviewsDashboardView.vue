<template>
    <v-card flat>
      <h2>Entrevistas</h2>
      <v-text-field
        v-model="search"
        append-inner-icon="fas fa-search"
        label="Pesquisar"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="interviews"
        :search="search"
        item-key="id"
        no-data-text="Nenhuma entrevista a apresentar."
      >
      </v-data-table>
    </v-card>
</template>
  
<script setup lang="ts">
  import type InterviewDto from '@/models/interviews/InterviewDto';
  import RemoteServices from '@/services/RemoteServices';
  import { reactive, ref } from 'vue';
  
  let search = ref('');
  const headers = [
    { title: 'ID', value: 'id', sortable: true, filterable: false },
    { title: 'Nome do Concurso', value: 'callName', sortable: true, filterable: true },
    { title: 'Sala', value: 'room', sortable: true, filterable: true},
    { title: 'Candidato', value: 'candidate', sortable: true, filterable: true },
    { title: 'Estado', value: 'status', sortable: true, filterable: true },
    // TODO: maybe add date?
    { title: 'Ações', value: 'actions', sortable: false, filterable: false }
  ];
  
  let interviews: InterviewDto[] = reactive([]);
  
  RemoteServices.getInterviews().then((data) => {
    interviews.push(...data);
  });
</script>
  