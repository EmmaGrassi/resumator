

const template = `

<style>
    .resume {
        background-color: white;
        padding: 1em;
        color: #000;
        max-width: 800px;
        width: 100%;
        margin: 0 auto;
    }

    .section,
    .experience {
      margin-bottom: 1em;
      padding-bottom: 1em;
    }

    .experience {
      border-bottom: 1px dashed #999;
    }

    .experience:last-child {
      border-bottom: none;
    }
</style>


<!--- First demo template -->
<div class="resume">
  <div class="profile section">
    <div><b>Name</b>: {{name}} {{surname}}</div>
    <div><b>Date of birth</b>: {{dateOfBirth}}</div>
    <div><b>About</b>: {{{aboutMe}}}</div>
  </div>

  <div class="experience section">
  {{#experience}}
    <div class="experience">
      <div><b>Name</b>: {{companyName}}</div>
      <div><b>Role</b>: {{role}}</div>
      <div><b>Summery</b>: {{shortDescription}}</div>
    </div>
  {{/experience}}
  </div>
</div>`;
export default template;
