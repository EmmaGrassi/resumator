import _ from 'lodash';
import React from 'react';
import {
  Button,
  ButtonToolbar,
  ButtonGroup,
  Col,
  Row,
  Glyphicon
} from 'react-bootstrap';

import log from 'loglevel';

class PaginationComponent extends React.Component {
  constructor(options) {
    super(options);

    this.options = options;

    this.url = `#/cms/users`;
  }

  navigateToPage(page, limit) {
    return () => {
      // TODO: Implement with the router
      // this.page.listUsers(page, limit);
    };
  }

  render() {
    log.debug('PaginationComponent#render');

    // TODO: Fix this.
    /*
    const listPagination = this.domainStore.state.listPagination;

    const currentPageNumber = listPagination.page;
    const firstPageNumber = 1;
    const lastPageNumber = listPagination.total;
    const nextPageNumber = currentPageNumber + 1 > lastPageNumber && currentPageNumber + 1 || lastPageNumber;
    const previousPageNumber = currentPageNumber - 1 < firstPageNumber && currentPageNumber - 1 || firstPageNumber;

    const firstPageButtonIsDisabled = currentPageNumber === 1;
    const lastPageButtonIsDisabled = currentPageNumber === listPagination.total;
    const nextPageButtonIsDisabled = currentPageNumber >= lastPageNumber;
    const previousPageButtonIsDisabled = currentPageNumber <= firstPageNumber;

    const firstPageButton = (
      <Button
        disabled={firstPageButtonIsDisabled}
        onClick={this.navigateToPage(firstPageNumber, listPagination.limit)}
      >
        <Glyphicon glyph="step-backward"/>
      </Button>
    );

    const lastPageButton = (
      <Button
        disabled={lastPageButtonIsDisabled}
        onClick={this.navigateToPage(lastPageNumber, listPagination.limit)}
      >
        <Glyphicon glyph="step-forward"/>
      </Button>
    );

    const nextPageButton = (
      <Button
        disabled={nextPageButtonIsDisabled}
        onClick={this.navigateToPage(nextPageNumber, listPagination.limit)}
      >
        <Glyphicon glyph="forward"/>
      </Button>
    );

    const previousPageButton = (
      <Button
        disabled={previousPageButtonIsDisabled}
        onClick={this.navigateToPage(previousPageNumber, listPagination.limit)}
      >
        <Glyphicon glyph="backward"/>
      </Button>
    );

    let pageButtonsRange;

    if (listPagination.total) {
      pageButtonsRange = _.range(1, listPagination.total + 1);
    } else {
      pageButtonsRange = [1];
    }

    const pageButtons = _.map(pageButtonsRange, (v, i) => {
      const href = `#/cms/users?page=${v}`;

      // Don't show the dots links.
      if (v === '...') {
        return '';
      }

      return (
        <Button
          key={v}
          active={v === currentPageNumber}
          onClick={this.navigateToPage(v, listPagination.limit)}
        >
          {v}
        </Button>
      );
    });

    return (
      <Row>
        <Col xs={12} className="col-center">
          <ButtonToolbar>
            <ButtonGroup>
              {firstPageButton}
              {previousPageButton}
              {pageButtons}
              {nextPageButton}
              {lastPageButton}
            </ButtonGroup>
          </ButtonToolbar>
        </Col>
      </Row>
    );
    */

    return (
      <div></div>
    );
  }
}

PaginationComponent.displayName = 'PaginationComponent';

PaginationComponent.contextTypes = {
  application: React.PropTypes.object
};

export default PaginationComponent;
