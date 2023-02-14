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
        multi-sort
        no-data-text="Nenhuma entrevista a apresentar."
      > <!-- If I find a way to leave all groups open by default I'll add groupBy -->
        <template v-slot:item="{ item }">
          <tr>
            <!-- <td></td> --> <!-- Empty cell for Group -->
            <td style="padding-left: 1%" >{{ item.raw.id }}</td>
            <td>{{ item.raw.callName }}</td>
            <td>{{ item.raw.roomName }}</td>
            <td>{{ item.raw.candidateName }}</td>
            <td>{{ item.raw.status }}</td>
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
  import type InterviewDto from '@/models/interviews/InterviewDto';
  import RemoteServices from '@/services/RemoteServices';
  import { reactive, ref } from 'vue';
  
  let search = ref('');
  // const groupBy = [ { title: 'Concurso', key: 'callName', value: 'callName' } ];
  const headers = [
    { title: 'ID', value: 'id', key: 'id', sortable: true, filterable: false },
    { title: 'Nome do Concurso', value: 'callName', key: 'callName', sortable: true, filterable: true },
    { title: 'Sala', value: 'roomName', key: 'roomName', sortable: true, filterable: true},
    { title: 'Candidato', value: 'candidateName', key: 'candidateName', sortable: true, filterable: true },
    { title: 'Estado', value: 'status', key: 'status', sortable: true, filterable: true },
    { title: 'Ações', value: 'actions', sortable: false, filterable: false }
  ];
  
  let interviews: InterviewDto[] = reactive([]);
  
  RemoteServices.getInterviews().then((data) => {
    interviews.push(...data);

    // TODO: remove the as any?
    // TODO: consider if note worthy adding else statements to the if's
    interviews.forEach(async (interview) => {
      interview.callName = 'Pendente';
      interview.roomName = 'Pendente';
      interview.candidateName = 'Pendente';

      if (interview.callId) {
        // TODO: catch errors
        const call = await RemoteServices.getCall(interview.callId);
        interview.callName = (call as any).name;
      }
      if (interview.roomId) {
        const room = await RemoteServices.getRoom(interview.roomId);
        interview.roomName = (room as any).name;
      }
      if (interview.candidateId) {
        const candidate = await RemoteServices.getCandidate(interview.candidateId);
        interview.candidateName = (candidate as any).name;
      }
    });
  });

</script>
  